package com.ecchilon.happypandaproject.sites.test;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.ecchilon.happypandaproject.GalleryAdapter;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.imageviewer.ImageViewerItem;
import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;
import com.ecchilon.happypandaproject.util.VolleySingleton;

/**
 * Created by Alex on 5/4/2014.
 */
public class DummyAdapter extends GalleryAdapter {

	public DummyAdapter(GalleryOverviewModuleInterface galleryInterface,
			GalleryItemClickListener itemClickListener) {
		super(galleryInterface, itemClickListener);
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		View innerView = null;
		Context c = viewGroup.getContext();

		if(view == null) {
			view = View.inflate(c, R.layout.gallery_item, null);
		}

		final ImageViewerItem currentItem = getItem(i);

		((TextView) view.findViewById(R.id.item_title)).setText(currentItem.getTitle());

		//set all gallery item values
		if(currentItem.getThumbUrl() != null) {
			NetworkImageView networkImageView = (NetworkImageView)view.findViewById(R.id.item_thumb);
			networkImageView.setImageUrl(currentItem.getThumbUrl(), VolleySingleton.getImageLoader());
		}

		//set up click calls
		view.findViewById(R.id.item_favorite).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getGalleryItemClickListener().GalleryItemFavoriteClicked(currentItem);
			}
		});
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getGalleryItemClickListener().GalleryItemClicked(currentItem);
			}
		});

		return  view;
	}
}
