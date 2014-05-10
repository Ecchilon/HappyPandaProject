package com.ecchilon.happypandaproject.sites.test;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.ecchilon.happypandaproject.GalleryPageAdapter;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.favorites.FavoritesLoader;
import com.ecchilon.happypandaproject.navigation.navitems.INavItem;
import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;
import com.ecchilon.happypandaproject.util.VolleySingleton;

/**
 * Created by Alex on 5/4/2014.
 */
public class DummyPageAdapter extends GalleryPageAdapter<DummyMangaItem> {

	Map<String, INavItem> dummyNavItems;

	public DummyPageAdapter(GalleryOverviewModuleInterface galleryInterface,
			GalleryItemClickListener itemClickListener, FavoritesLoader favoritesLoader) {
		super(galleryInterface, itemClickListener, favoritesLoader);

		dummyNavItems = new HashMap<String, INavItem>();
		dummyNavItems.put("Dummy 1", new DummyNavItem("Dummy 1"));
		dummyNavItems.put("Dummy 2", new DummyNavItem("Dummy 2"));
		dummyNavItems.put("Dummy 3", new DummyNavItem("Dummy 3"));
		dummyNavItems.put("Dummy 4", new DummyNavItem("Dummy 4"));
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		Context c = viewGroup.getContext();

		if (view == null) {
			view = View.inflate(c, R.layout.dummy_gallery_item, null);
		}

		final DummyMangaItem currentItem = getItem(i);

		((TextView) view.findViewById(R.id.item_title)).setText(currentItem.getTitle());

		//set all gallery item values
		if (currentItem.getThumbUrl() != null) {
			NetworkImageView networkImageView = (NetworkImageView) view.findViewById(R.id.item_thumb);
			networkImageView.setImageUrl(currentItem.getThumbUrl(), VolleySingleton.getImageLoader());
		}

		setupFavoriteButton((ImageView) view.findViewById(R.id.item_favorite), currentItem);

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

	/**
	 * Enables or disables the favorite button based on whether the item is already in favorites
	 *
	 * @param view
	 * @param item
	 */
	private void setupFavoriteButton(ImageView view, final DummyMangaItem item) {

		if (isFavorite(item)) {
			view.setImageResource(R.drawable.ic_action_favorite_disabled);
		}
		else {
			view.setImageResource(R.drawable.ic_action_favorite);
		}

		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getGalleryItemClickListener().GalleryItemFavoriteClicked(item);
			}
		});
	}
}
