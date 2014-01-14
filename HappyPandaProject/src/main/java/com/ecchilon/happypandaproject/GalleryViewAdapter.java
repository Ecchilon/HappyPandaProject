package com.ecchilon.happypandaproject;

import android.view.View;
import android.view.ViewGroup;

import sites.GalleryOverviewInterface;
import util.PagedScrollAdapter;

/**
 * Created by Alex on 1/4/14.
 */
public class GalleryViewAdapter extends PagedScrollAdapter<GalleryItem> {

    public interface PageCreationFailedListener {
        public void PageCreationFailed();
    }

    private  PageCreationFailedListener mListener;

    private GalleryOverviewInterface mGalleryInterface;

    public GalleryViewAdapter(GalleryOverviewInterface galleryInterface) {
        mGalleryInterface = galleryInterface;
    }

    public void setPageCreationFailedListener(PageCreationFailedListener _listener){
        mListener = _listener;
    }

    @Override
    public void loadNewDataSet() {
        mGalleryInterface.getPage(getCurrentPage(), this);
    }

    @Override
    public void PageCreationFailed() {
        if(mListener != null)
            mListener.PageCreationFailed();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
            view = View.inflate(viewGroup.getContext(), R.layout.gallery_item, null);

        //TODO do stuff with gallery item
        GalleryItem currentItem = getItem(i);



        return  view;
    }
}
