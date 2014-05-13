package com.ecchilon.happypandaproject.sites.util;

import com.ecchilon.happypandaproject.drawer.INavVisitor;
import com.ecchilon.happypandaproject.gallery.navitems.FavoritesNavItem;
import com.ecchilon.happypandaproject.gallery.navitems.INavItem;
import com.ecchilon.happypandaproject.gallery.navitems.LibraryNavPage;
import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.sites.GalleryPagesModuleInterface;
import com.ecchilon.happypandaproject.sites.test.DummyImageModuleInterface;
import com.ecchilon.happypandaproject.sites.test.DummyNavItem;

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

		//TODO search library as well!
		@Override
		public INavItem execute(LibraryNavPage libraryNavPage) {
			return null;
		}
	}
}
