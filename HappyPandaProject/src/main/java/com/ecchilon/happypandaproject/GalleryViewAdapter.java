package com.ecchilon.happypandaproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;
import com.ecchilon.happypandaproject.util.PagedScrollAdapter;

/**
 * Created by Alex on 1/4/14.
 */
public class GalleryViewAdapter extends PagedScrollAdapter<GalleryItem> {

    public interface PageCreationFailedListener {
        public void PageCreationFailed();
    }

    public interface GalleryItemClickListener {
        public void GalleryItemClicked(GalleryItem item);
        public void GalleryItemDownloadClicked(GalleryItem item);
        public void GalleryItemFavoriteClicked(GalleryItem item);
    }

    private PageCreationFailedListener mListener;
    private GalleryItemClickListener mGalleryItemClickListener;
    private GalleryOverviewModuleInterface mGalleryInterface;

    private int mInnerLayoutId;

    public GalleryViewAdapter(GalleryOverviewModuleInterface galleryInterface, GalleryItemClickListener itemClickListener, Context c) {
        mGalleryInterface = galleryInterface;
        mGalleryItemClickListener = itemClickListener;

        mInnerLayoutId = c.getResources().getIdentifier(mGalleryInterface.getInnerLayoutName(),"layout", c.getPackageName());
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

        if(view == null) {
            view = View.inflate(c, R.layout.gallery_item, null);
            if(mInnerLayoutId != 0){
                FrameLayout container = ((FrameLayout)view.findViewById(R.id.inner_view));
                innerView = View.inflate(c, mInnerLayoutId, container);
            }
        }
        else
        {
            innerView = ((FrameLayout)view.findViewById(R.id.inner_view)).getChildAt(0);
        }

        final GalleryItem currentItem = getItem(i);

        ((TextView) view.findViewById(R.id.item_title)).setText(currentItem.getTitle());

        //set all gallery item values
        if(currentItem.getThumbUrl() != null) {
            NetworkImageView networkImageView = (NetworkImageView)view.findViewById(R.id.item_thumb);
            networkImageView.setImageUrl(currentItem.getThumbUrl(), GalleryOverviewActivity.getImageLoader());
        }

        //set up click calls
        view.findViewById(R.id.item_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGalleryItemClickListener.GalleryItemDownloadClicked(currentItem);
            }
        });
        view.findViewById(R.id.item_favorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGalleryItemClickListener.GalleryItemFavoriteClicked(currentItem);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGalleryItemClickListener.GalleryItemClicked(currentItem);
            }
        });

        //only call if it's been found. Could just as well be left empty
        if(innerView != null)
            mGalleryInterface.setCardInnerContentView(currentItem, innerView);

        return  view;
    }
}
