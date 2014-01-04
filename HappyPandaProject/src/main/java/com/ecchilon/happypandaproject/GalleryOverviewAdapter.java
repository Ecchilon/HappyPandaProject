package com.ecchilon.happypandaproject;

import android.view.View;
import android.view.ViewGroup;

import sites.siteutil.GalleryOverviewInterface;
import util.PagedScrollAdapter;

/**
 * Created by Alex on 1/4/14.
 */
public class GalleryOverviewAdapter extends PagedScrollAdapter<GalleryItem> {

    private GalleryOverviewInterface mGalleryInterface;

    public GalleryOverviewAdapter(GalleryOverviewInterface galleryInterface) {
        mGalleryInterface = galleryInterface;
    }

    @Override
    public void loadNewDataSet() {
        mGalleryInterface.nextPage(getCurrentPage(), this);
    }

    @Override
    public void PageCreationFailed() {
        //TODO display end of gallery
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
