package com.ecchilon.happypandaproject.sites.util;

import com.ecchilon.happypandaproject.GalleryItem;
import com.ecchilon.happypandaproject.navigation.INavVisitor;
import com.ecchilon.happypandaproject.navigation.navitems.BookmarkedNavItem;
import com.ecchilon.happypandaproject.navigation.navitems.INavItem;
import com.ecchilon.happypandaproject.navigation.navitems.OverviewNavItem;
import com.ecchilon.happypandaproject.sites.AlbumOverviewModuleInterface;
import com.ecchilon.happypandaproject.sites.AlbumPagesModuleInterface;
import com.ecchilon.happypandaproject.sites.test.DummyAlbumModuleInterface;
import com.ecchilon.happypandaproject.sites.test.DummyImageModuleInterface;
import com.ecchilon.happypandaproject.sites.test.DummySearchModuleInterface;

/**
 * Created by Alex on 1/4/14.
 */
public class SiteFactory {
	private static SiteNavVisitor mVisitor = new SiteNavVisitor();
	private static SearchNavVisitor mSearchVisitor = new SearchNavVisitor();

	/**
	 * Returns the site interface based on the passed INavItem
	 * @param index
	 * @return
	 */
    public static AlbumOverviewModuleInterface getOverviewInterface(INavItem index) {
        return index.visit(mVisitor);
    }

	/**
	 * Returns the search interface based on the passed INavItem and String
	 * @param index
	 * @param query
	 * @return
	 */
    public static AlbumOverviewModuleInterface getSearchInterface(INavItem index, String query) {
		mSearchVisitor.setQuery(query);
	    return index.visit(mSearchVisitor);
    }

	//TODO should probably be separated from the overview modules
    public static AlbumPagesModuleInterface getGalleryPagesInterface(GalleryItem item) {
        return new DummyImageModuleInterface();
    }

	private static class SiteNavVisitor implements INavVisitor<AlbumOverviewModuleInterface> {

		@Override
		public AlbumOverviewModuleInterface execute(BookmarkedNavItem simpleNavItem) {
			return new DummyAlbumModuleInterface();
		}

		@Override
		public AlbumOverviewModuleInterface execute(OverviewNavItem overviewNavItem) {
			return new DummyAlbumModuleInterface();
		}
	}

	private static class SearchNavVisitor implements INavVisitor<AlbumOverviewModuleInterface>{

		private String mQuery;

		public void setQuery(String query){
			mQuery = query;
		}

		@Override
		public AlbumOverviewModuleInterface execute(BookmarkedNavItem simpleNavItem) {
			return new DummySearchModuleInterface(mQuery);
		}

		@Override
		public AlbumOverviewModuleInterface execute(OverviewNavItem overviewNavItem) {
			return new DummySearchModuleInterface(mQuery);
		}
	}
}
