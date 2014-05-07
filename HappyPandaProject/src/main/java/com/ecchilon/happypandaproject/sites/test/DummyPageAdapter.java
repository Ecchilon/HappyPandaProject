package com.ecchilon.happypandaproject.sites.test;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.ecchilon.happypandaproject.GalleryPageAdapter;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.imageviewer.ImageViewerItem;
import com.ecchilon.happypandaproject.navigation.navitems.INavPage;
import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;
import com.ecchilon.happypandaproject.util.VolleySingleton;

/**
 * Created by Alex on 5/4/2014.
 */
public class DummyPageAdapter extends GalleryPageAdapter {

	Map<String, INavPage> dummyNavItems;

	public DummyPageAdapter(GalleryOverviewModuleInterface galleryInterface,
			GalleryItemClickListener itemClickListener) {
		super(galleryInterface, itemClickListener);

		dummyNavItems = new HashMap<String, INavPage>();
		dummyNavItems.put("Dummy 1", new DummyNavPage("Dummy 1"));
		dummyNavItems.put("Dummy 2", new DummyNavPage("Dummy 2"));
		dummyNavItems.put("Dummy 3", new DummyNavPage("Dummy 3"));
		dummyNavItems.put("Dummy 4", new DummyNavPage("Dummy 4"));
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		Context c = viewGroup.getContext();

		if (view == null) {
			view = View.inflate(c, R.layout.gallery_item, null);
		}

		final ImageViewerItem currentItem = getItem(i);

		((TextView) view.findViewById(R.id.item_title)).setText(currentItem.getTitle());

		//set all gallery item values
		if (currentItem.getThumbUrl() != null) {
			NetworkImageView networkImageView = (NetworkImageView) view.findViewById(R.id.item_thumb);
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

		view.findViewById(R.id.item_overflow).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showOverflowView(view, dummyNavItems);
			}
		});

		return view;
	}
}
