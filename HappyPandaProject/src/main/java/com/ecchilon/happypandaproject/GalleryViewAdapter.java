package com.ecchilon.happypandaproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ecchilon.happypandaproject.sites.GalleryOverviewInterface;
import com.ecchilon.happypandaproject.util.PagedScrollAdapter;

/**
 * Created by Alex on 1/4/14.
 */
public class GalleryViewAdapter extends PagedScrollAdapter<GalleryItem> {

    public interface PageCreationFailedListener {
        public void PageCreationFailed();
    }

    private PageCreationFailedListener mListener;

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
        View innerView = null;
        Context c = viewGroup.getContext();

        int layoutId = c.getResources().getIdentifier(mGalleryInterface.getInnerLayoutName(),"layout", c.getPackageName());

        if(view == null) {
            view = View.inflate(c, R.layout.gallery_item, null);

            if(layoutId != 0){
                FrameLayout container = ((FrameLayout)view.findViewById(R.id.inner_view));
                innerView = View.inflate(c, layoutId, container);
            }
        }
        else
        {
            innerView = ((FrameLayout)view.findViewById(R.id.inner_view)).getChildAt(0);
        }

        //TODO do stuff with gallery item
        GalleryItem currentItem = getItem(i);

        ((TextView)view.findViewById(R.id.item_title)).setText(currentItem.getTitle());

        //only call if it's been found. Could just as well be left empty
        if(innerView != null)
            mGalleryInterface.setInnerContentView(currentItem, innerView);

        return  view;
    }
}
