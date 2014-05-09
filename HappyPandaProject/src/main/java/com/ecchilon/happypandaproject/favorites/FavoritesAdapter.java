package com.ecchilon.happypandaproject.favorites;

import android.view.View;
import android.view.ViewGroup;
import com.ecchilon.happypandaproject.GalleryPageAdapter;
import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;

/**
 * Created by Alex on 9-5-2014.
 */
public class FavoritesAdapter extends GalleryPageAdapter {

	public FavoritesAdapter(GalleryOverviewModuleInterface galleryInterface,
			GalleryItemClickListener itemClickListener) {
		super(galleryInterface, itemClickListener);
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		return null;
	}
}
