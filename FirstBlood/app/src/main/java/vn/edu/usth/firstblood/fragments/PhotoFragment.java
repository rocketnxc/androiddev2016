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
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.firstblood.R;

/**
 * Created by vutngson on 3/25/16.
 */
public class PhotoFragment extends Fragment {
    private JSONObject jsonObjectUserPhoto;
    private GridView gridView;


    public PhotoFragment() {
    }

    public JSONObject getJsonObjectUserPhoto() {
        return jsonObjectUserPhoto;
    }

    public void setJsonObjectUserPhoto(JSONObject jsonObjectUserPhoto) {
        this.jsonObjectUserPhoto = jsonObjectUserPhoto;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        Log.i("UserPhoto3", jsonObjectUserPhoto.toString());
        List<Bitmap> pictureArray = new ArrayList<Bitmap>();
        gridView = (GridView) view.findViewById(R.id.gridView);

        try {
            JSONArray jsonArray = jsonObjectUserPhoto.getJSONArray("data");
            Log.i("UserPhotoArray", jsonArray.toString());

            for(int i=0;i<3;i++){
                JSONObject e = jsonArray.getJSONObject(i);
                Log.i("UserPhotoItem" , e.getString("picture"));
                DownloadShowImage(e.getString("picture"), pictureArray);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<Bitmap> arrayAdapter = new ArrayAdapter<Bitmap>(getActivity().getBaseContext(), R.layout.photoitem_photo , pictureArray);
        gridView.setAdapter(arrayAdapter);
        return view;
    }

    private static void DownloadShowImage(String url, final List pictureArray) {
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
                    pictureArray.add(bitmap);
                } else {
                    Log.i("CoverExist", "Image Does Not exist or Network Error");
                }

            }
        }.execute(url);
    }
}
