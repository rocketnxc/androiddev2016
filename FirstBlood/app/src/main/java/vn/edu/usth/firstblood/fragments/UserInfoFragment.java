package vn.edu.usth.firstblood.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.edu.usth.firstblood.FacebookClasses.UserInfo;
import vn.edu.usth.firstblood.R;

/**
 * Created by vutngson on 3/24/16.
 */
public class UserInfoFragment extends Fragment {


    private JSONObject jsonObjectUserInfo;
    private UserInfo userInfo;
    private ImageView cover;
    private ListView listView;

    public UserInfoFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //create a view
        View view = inflater.inflate(R.layout.fragment_userinfo, container, false);
        cover = (ImageView) view.findViewById(R.id.cover);
        listView = (ListView) view.findViewById(R.id.listview_userinfo);

        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> hm = new HashMap<String, String>();

        //create userinfo object
        userInfo = new UserInfo();
        //set attribute for userinfo object
        Log.i("UserInfos3", jsonObjectUserInfo.toString());
        try {
            if (jsonObjectUserInfo.has("id")) {
                userInfo.setId(jsonObjectUserInfo.getString("id"));
                Log.i("CheckUserInfo", userInfo.getId());
                hm = new HashMap<String, String>();
                hm.put("title", "ID");
                hm.put("info", userInfo.getId());
                aList.add(hm);
            }
            if (jsonObjectUserInfo.has("name")) {
                userInfo.setName(jsonObjectUserInfo.getString("name"));
                Log.i("CheckUserInfo", userInfo.getName());
                hm = new HashMap<String, String>();
                hm.put("title", "Name");
                hm.put("info", userInfo.getName());
                aList.add(hm);
            }
            if (jsonObjectUserInfo.has("gender")) {
                userInfo.setGender(jsonObjectUserInfo.getString("gender"));
                Log.i("CheckUserInfo", userInfo.getGender());
                hm = new HashMap<String, String>();
                hm.put("title", "Gender");
                hm.put("info", userInfo.getGender());
                aList.add(hm);
            }

            if (jsonObjectUserInfo.has("age_range")) {
                userInfo.setAge_range(jsonObjectUserInfo.getString("age_range"));
                Log.i("CheckUserInfo", userInfo.getAge_range());

            }
            if (jsonObjectUserInfo.has("birthday")) {
                userInfo.setBirthday(jsonObjectUserInfo.getString("birthday"));
                Log.i("CheckUserInfo", userInfo.getBirthday());
                hm = new HashMap<String, String>();
                hm.put("title", "Birthday");
                hm.put("info", userInfo.getBirthday());
                aList.add(hm);
            }
            if (jsonObjectUserInfo.has("cover")) {
                userInfo.setCover(jsonObjectUserInfo.getString("cover"));
                Log.i("CheckUserInfo", userInfo.getCover());
                String url = userInfo.getCover();
                DownloadShowImage(url, cover);
            }
            if (jsonObjectUserInfo.has("education")) {
                userInfo.setEducation(jsonObjectUserInfo.getString("education"));
                Log.i("CheckUserInfo", userInfo.getEducation());
                hm = new HashMap<String, String>();
                hm.put("title", "Education");
                hm.put("info", userInfo.getEducation());
                aList.add(hm);
            }
            if (jsonObjectUserInfo.has("email")) {
                userInfo.setEmail(jsonObjectUserInfo.getString("email"));
                Log.i("CheckUserInfo", userInfo.getEmail());
                hm = new HashMap<String, String>();
                hm.put("title", "Email");
                hm.put("info", userInfo.getEmail());
                aList.add(hm);
            }

            if (jsonObjectUserInfo.has("hometown")) {
                userInfo.setHometown(jsonObjectUserInfo.getString("hometown"));
                Log.i("CheckUserInfo", userInfo.getHometown());
                hm = new HashMap<String, String>();
                hm.put("title", "Hometown");
                hm.put("info", userInfo.getHometown());
                aList.add(hm);
            }
            if (jsonObjectUserInfo.has("about")) {
                userInfo.setAbout(jsonObjectUserInfo.getString("about"));
                Log.i("CheckUserInfo", userInfo.getAbout());
                hm = new HashMap<String, String>();
                hm.put("title", "About");
                hm.put("info", userInfo.getAbout());
                aList.add(hm);
            }
            if (jsonObjectUserInfo.has("picture")) {
                userInfo.setPicture(jsonObjectUserInfo.getString("picture"));
                Log.i("CheckUserInfo", userInfo.getPicture());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String[] from = {"title", "info"};
        int[] to = {R.id.title_userinfo, R.id.info_userinfo};
        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.listviewitem_userinfo, from, to);
        listView.setAdapter(adapter);
        return view;
    }

    private static void DownloadShowImage(String url, final ImageView cover) {
        new AsyncTask<String, Integer, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                URL url = null;
                Bitmap bitmap = null;
                try {
                    url = new URL(params[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    int response = connection.getResponseCode();
                    Log.i("CoverResponse", "The reponse is " + response);
                    InputStream inputStream = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {

                if (bitmap != null) {
                    cover.setImageBitmap(bitmap);
                } else {
                    Log.i("CoverExist", "Image Does Not exist or Network Error");
                }

            }
        }.execute(url);
    }


    public JSONObject getJsonObjectUserInfo() {
        return jsonObjectUserInfo;
    }

    public void setJsonObjectUserInfo(JSONObject jsonObjectUserInfo) {
        this.jsonObjectUserInfo = jsonObjectUserInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
