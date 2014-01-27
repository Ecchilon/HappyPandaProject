package com.ecchilon.happypandaproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.ecchilon.happypandaproject.sites.AlbumOverviewModuleInterface;
import com.ecchilon.happypandaproject.util.PagedScrollAdapter;
import com.ecchilon.happypandaproject.util.VolleySingleton;

/**
 * Created by Alex on 1/4/14.
 */
public class AlbumOverviewAdapter extends PagedScrollAdapter<AlbumItem> {

    public interface PageCreationFailedListener {
        public void PageCreationFailed();
    }

    public interface AlbumItemClickListener {
        public void AlbumItemClicked(AlbumItem item);
        public void AlbumItemDownloadClicked(AlbumItem item);
        public void AlbumItemFavoriteClicked(AlbumItem item);
    }

    private PageCreationFailedListener mListener;
    private AlbumItemClickListener mAlbumItemClickListener;
    private AlbumOverviewModuleInterface mAlbumInterface;

    private int mInnerLayoutId;

    public AlbumOverviewAdapter(AlbumOverviewModuleInterface galleryInterface, AlbumItemClickListener itemClickListener, Context c) {
        mAlbumInterface = galleryInterface;
        mAlbumItemClickListener = itemClickListener;

        mInnerLayoutId = c.getResources().getIdentifier(mAlbumInterface.getInnerLayoutName(),"layout", c.getPackageName());
    }

    public void setPageCreationFailedListener(PageCreationFailedListener _listener){
        mListener = _listener;
    }

    @Override
    public void loadNewDataSet() {
        mAlbumInterface.getPage(getCurrentPage(), this);
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
            view = View.inflate(c, R.layout.album_item, null);
            if(mInnerLayoutId != 0){
                FrameLayout container = ((FrameLayout)view.findViewById(R.id.inner_view));
                innerView = View.inflate(c, mInnerLayoutId, container);
            }
        }
        else
        {
            innerView = ((FrameLayout)view.findViewById(R.id.inner_view)).getChildAt(0);
        }

        final AlbumItem currentItem = getItem(i);

        ((TextView) view.findViewById(R.id.item_title)).setText(currentItem.getTitle());

        //set all gallery item values
        if(currentItem.getThumbUrl() != null) {
            NetworkImageView networkImageView = (NetworkImageView)view.findViewById(R.id.item_thumb);
            networkImageView.setImageUrl(currentItem.getThumbUrl(), VolleySingleton.getImageLoader());
        }

        //set up click calls
        view.findViewById(R.id.item_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAlbumItemClickListener.AlbumItemDownloadClicked(currentItem);
            }
        });
        view.findViewById(R.id.item_favorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAlbumItemClickListener.AlbumItemFavoriteClicked(currentItem);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAlbumItemClickListener.AlbumItemClicked(currentItem);
            }
        });

        //only call if it's been found. Could just as well be left empty
        if(innerView != null)
            mAlbumInterface.setCardInnerContentView(currentItem, innerView);

        return  view;
    }
}
