package com.ecchilon.happypandaproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ecchilon on 1/4/14.
 */
public class GalleryOverviewFragment extends Fragment{
    public GalleryOverviewFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gallery_fragment_overview, container, false);

        return rootView;
    }
}
