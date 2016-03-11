package vn.edu.usth.firstblood.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import vn.edu.usth.firstblood.R;

/**
 * Created by sontngvu on 3/11/16.
 */
public class NewsFeedFragment extends Fragment {
    public NewsFeedFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = new View(getActivity());
        view = inflater.inflate(R.layout.fragment_news_feed, container, false);
        return view;
    }
}


