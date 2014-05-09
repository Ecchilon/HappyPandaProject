package com.ecchilon.happypandaproject.sites.util;

import android.content.Context;
import com.ecchilon.happypandaproject.GalleryPageAdapter;
import com.ecchilon.happypandaproject.favorites.FavoritesAdapter;
import com.ecchilon.happypandaproject.favorites.FavoritesInterface;
import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.navigation.INavVisitor;
import com.ecchilon.happypandaproject.navigation.navitems.FavoritesNavItem;
import com.ecchilon.happypandaproject.navigation.navitems.INavItem;
import com.ecchilon.happypandaproject.sites.GalleryPagesModuleInterface;
import com.ecchilon.happypandaproject.sites.test.DummyGalleryModuleInterface;
import com.ecchilon.happypandaproject.sites.test.DummyImageModuleInterface;
import com.ecchilon.happypandaproject.sites.test.DummyNavItem;
import com.ecchilon.happypandaproject.sites.test.DummyPageAdapter;

/**
 * Created by Alex on 1/4/14.
 */
public class SiteFactory {

	/**
	 * Returns the menu_search interface based on the passed INavItem and String
	 *
	 * @param index
	 * @param query
	 * @return
	 */
	public static INavItem getSearchItem(INavItem index, String query) {
		return index.visit(new SearchNavVisitor(query));
	}

	/**
	 * Returns a new @GalleryPageAdapter to use in the gallery view
	 *
	 * @param item
	 * @param listener
	 * @return
	 */
	public static GalleryPageAdapter getGalleryAdapter(INavItem item,
			GalleryPageAdapter.GalleryItemClickListener listener, Context context) {
		return item.visit(new GalleryAdapterVisitor(listener, context));
	}

	//TODO should probably be separated from the overview modules
	public static GalleryPagesModuleInterface getGalleryPagesInterface(IMangaItem item) {
		return new DummyImageModuleInterface();
	}

	private static class SearchNavVisitor implements INavVisitor<INavItem> {

		private String mQuery;

		public SearchNavVisitor(String query) {
			mQuery = query;
		}

		@Override
		public INavItem execute(DummyNavItem dummyNavItem) {
			return new DummyNavItem(mQuery);
		}

		//TODO allow user to search and return available favorites based on search
		@Override
		public INavItem execute(FavoritesNavItem favoritesNavItem) {
			return null;
		}
	}

	private static class GalleryAdapterVisitor implements INavVisitor<GalleryPageAdapter> {
		private GalleryPageAdapter.GalleryItemClickListener mListener;
		private Context mAppContext;

		public GalleryAdapterVisitor(GalleryPageAdapter.GalleryItemClickListener clickListener, Context context) {
			mListener = clickListener;
			mAppContext = context.getApplicationContext();
		}

		@Override
		public GalleryPageAdapter execute(DummyNavItem dummyNavItem) {
			DummyGalleryModuleInterface moduleInterface = new DummyGalleryModuleInterface();
			return new DummyPageAdapter(moduleInterface, mListener);
		}

		@Override
		public GalleryPageAdapter execute(FavoritesNavItem favoritesNavItem) {
			FavoritesInterface favoritesInterface = new FavoritesInterface(mAppContext);
			return new FavoritesAdapter(favoritesInterface, mListener);
		}
	}
}
