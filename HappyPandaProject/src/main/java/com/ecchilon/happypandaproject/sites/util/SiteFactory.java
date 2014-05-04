package com.ecchilon.happypandaproject.sites.util;

import com.ecchilon.happypandaproject.imageviewer.ImageViewerItem;
import com.ecchilon.happypandaproject.navigation.INavVisitor;
import com.ecchilon.happypandaproject.navigation.navitems.DummyNavItem;
import com.ecchilon.happypandaproject.navigation.navitems.INavItem;
import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;
import com.ecchilon.happypandaproject.sites.GalleryPagesModuleInterface;
import com.ecchilon.happypandaproject.sites.test.DummyGalleryModuleInterface;
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
    public static GalleryOverviewModuleInterface getOverviewInterface(INavItem index) {
        return index.visit(mVisitor);
    }

	/**
	 * Returns the menu_search interface based on the passed INavItem and String
	 * @param index
	 * @param query
	 * @return
	 */
    public static GalleryOverviewModuleInterface getSearchInterface(INavItem index, String query) {
		mSearchVisitor.setQuery(query);
	    return index.visit(mSearchVisitor);
    }

	//TODO should probably be separated from the overview modules
    public static GalleryPagesModuleInterface getGalleryPagesInterface(ImageViewerItem item) {
        return new DummyImageModuleInterface();
    }

	private static class SiteNavVisitor implements INavVisitor<GalleryOverviewModuleInterface> {

		@Override
		public GalleryOverviewModuleInterface execute(DummyNavItem dummyNavItem) {
			return new DummyGalleryModuleInterface();
		}
	}

	private static class SearchNavVisitor implements INavVisitor<GalleryOverviewModuleInterface>{

		private String mQuery;

		public void setQuery(String query){
			mQuery = query;
		}

		@Override
		public GalleryOverviewModuleInterface execute(DummyNavItem dummyNavItem) {
			return new DummySearchModuleInterface(mQuery);
		}
	}
}
