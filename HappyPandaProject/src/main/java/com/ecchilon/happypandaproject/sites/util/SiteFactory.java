package com.ecchilon.happypandaproject.sites.util;

import com.ecchilon.happypandaproject.drawer.INavVisitor;
import com.ecchilon.happypandaproject.gallery.navitems.FavoritesNavItem;
import com.ecchilon.happypandaproject.gallery.navitems.INavItem;
import com.ecchilon.happypandaproject.gallery.navitems.LibraryNavPage;
import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.imageviewer.IMangaVisitor;
import com.ecchilon.happypandaproject.sites.MangaModuleInterface;
import com.ecchilon.happypandaproject.sites.fakku.FakkuManga;
import com.ecchilon.happypandaproject.sites.fakku.FakkuMangaModule;
import com.ecchilon.happypandaproject.sites.fakku.FakkuNavItem;

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
	public static MangaModuleInterface getGalleryPagesInterface(IMangaItem item) {
		return item.visit(new GalleryVisitor());
	}

	private static class GalleryVisitor implements IMangaVisitor<MangaModuleInterface> {

		@Override
		public MangaModuleInterface execute(FakkuManga fakkuManga) {
			return new FakkuMangaModule(fakkuManga);
		}
	}

	private static class SearchNavVisitor implements INavVisitor<INavItem> {

		private String mQuery;

		public SearchNavVisitor(String query) {
			mQuery = query;
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

		@Override
		public INavItem execute(FakkuNavItem fakkuNavItem) {
			return null;
		}
	}
}
