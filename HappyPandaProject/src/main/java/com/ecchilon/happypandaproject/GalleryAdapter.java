package com.ecchilon.happypandaproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.ecchilon.happypandaproject.imageviewer.ImageViewerItem;
import com.ecchilon.happypandaproject.navigation.navitems.INavItem;
import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;
import com.ecchilon.happypandaproject.util.PagedScrollAdapter;
import com.ecchilon.happypandaproject.util.VolleySingleton;

/**
 * Created by Alex on 1/4/14.
 */
public abstract class GalleryAdapter extends PagedScrollAdapter<ImageViewerItem> {

	public interface PageCreationFailedListener {
		public void PageCreationFailed();
	}

	public interface GalleryItemClickListener {
		public void GalleryItemClicked(ImageViewerItem item);

		public void GalleryNavItemClicked(INavItem item);

		public void GalleryItemFavoriteClicked(ImageViewerItem item);
	}

	private PageCreationFailedListener mListener;
	private GalleryItemClickListener mGalleryItemClickListener;
	private GalleryOverviewModuleInterface mGalleryInterface;

	protected GalleryOverviewModuleInterface getInterface() {
		return mGalleryInterface;
	}

	protected GalleryItemClickListener getGalleryItemClickListener() {
		return mGalleryItemClickListener;
	}

	public GalleryAdapter(GalleryOverviewModuleInterface galleryInterface,
			GalleryItemClickListener itemClickListener) {
		mGalleryInterface = galleryInterface;
		mGalleryItemClickListener = itemClickListener;
	}

	public void setPageCreationFailedListener(PageCreationFailedListener _listener) {
		mListener = _listener;
	}

	@Override
	public void loadNewDataSet() {
		mGalleryInterface.getPage(getCurrentPage(), this);
	}

	@Override
	public void PageCreationFailed() {
		if (mListener != null) {
			mListener.PageCreationFailed();
		}
	}

	@Override
	public abstract View getView(int i, View view, ViewGroup viewGroup);
}
