package vn.edu.usth.firstblood.fragments;


import android.os.Bundle;

import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.edu.usth.firstblood.R;

public class FriendListFragment extends ListFragment {

    // Array of strings storing country names
    String[] name = new String[] {
            "Vu Tung Son",
            "Cao Xuan Ngoc",
            "Nghiem Viet Cuong",
            "Vu Tung Son",
            "Cao Xuan Ngoc",
            "Nghiem Viet Cuong",
            "Vu Tung Son",
            "Cao Xuan Ngoc",
            "Nghiem Viet Cuong",
            "Dam Vinh Hung"
    };

    // Array of integers points to images stored in /res/drawable/
    int[] pics = new int[]{
            R.drawable.son,
            R.drawable.son,
            R.drawable.son,
            R.drawable.son,
            R.drawable.son,
            R.drawable.son,
            R.drawable.son,
            R.drawable.son,
            R.drawable.son,
            R.drawable.son
    };

    // Array of strings to store currencies
    String[] countries = new String[]{
            "India",
            "Pakistan",
            "Sri Lanka",
            "China",
            "Bangladesh",
            "Nepal",
            "Afghanistan",
            "North Korea",
            "South Korea",
            "Japan"
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<10;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("name", name[i]);
            hm.put("cons", countries[i]);
            hm.put("pics", Integer.toString(pics[i]) );
            aList.add(hm);
        }
        String[] from = { "name","cons","pics" };
        int[] to = { R.id.name,R.id.countries,R.id.pics};
        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.fragment_friend_list, from, to);

        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);


    }

}
