package com.ecchilon.happypandaproject.sites.util;

import com.ecchilon.happypandaproject.GalleryPageAdapter;
import com.ecchilon.happypandaproject.imageviewer.ImageViewerItem;
import com.ecchilon.happypandaproject.navigation.INavVisitor;
import com.ecchilon.happypandaproject.navigation.navitems.INavPage;
import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;
import com.ecchilon.happypandaproject.sites.GalleryPagesModuleInterface;
import com.ecchilon.happypandaproject.sites.test.DummyGalleryModuleInterface;
import com.ecchilon.happypandaproject.sites.test.DummyImageModuleInterface;
import com.ecchilon.happypandaproject.sites.test.DummyNavPage;
import com.ecchilon.happypandaproject.sites.test.DummyPageAdapter;
import com.ecchilon.happypandaproject.sites.test.DummySearchModuleInterface;

/**
 * Created by Alex on 1/4/14.
 */
public class SiteFactory {
	/**
	 * Returns the site interface based on the passed INavPage
	 *
	 * @param index
	 * @return
	 */
	public static GalleryOverviewModuleInterface getOverviewInterface(INavPage index) {
		return index.visit(new SiteNavVisitor());
	}

	/**
	 * Returns the menu_search interface based on the passed INavPage and String
	 *
	 * @param index
	 * @param query
	 * @return
	 */
	public static GalleryOverviewModuleInterface getSearchInterface(INavPage index, String query) {
		return index.visit(new SearchNavVisitor(query));
	}

	/**
	 * Returns a new @GalleryPageAdapter to use in the gallery view
	 *
	 * @param item
	 * @param listener
	 * @return
	 */
	public static GalleryPageAdapter getGalleryAdapter(INavPage item, GalleryOverviewModuleInterface listInterface,
			GalleryPageAdapter.GalleryItemClickListener listener) {
		return item.visit(new GalleryAdapterVisitor(listInterface, listener));
	}

	//TODO should probably be separated from the overview modules
	public static GalleryPagesModuleInterface getGalleryPagesInterface(ImageViewerItem item) {
		return new DummyImageModuleInterface();
	}

	private static class SiteNavVisitor implements INavVisitor<GalleryOverviewModuleInterface> {

		@Override
		public GalleryOverviewModuleInterface execute(DummyNavPage dummyNavItem) {
			return new DummyGalleryModuleInterface();
		}
	}

	private static class SearchNavVisitor implements INavVisitor<GalleryOverviewModuleInterface> {

		private String mQuery;

		public SearchNavVisitor(String query) {
			mQuery = query;
		}

		@Override
		public GalleryOverviewModuleInterface execute(DummyNavPage dummyNavItem) {
			return new DummySearchModuleInterface(mQuery);
		}
	}

	private static class GalleryAdapterVisitor implements INavVisitor<GalleryPageAdapter> {
		private GalleryOverviewModuleInterface mInterface;
		private GalleryPageAdapter.GalleryItemClickListener mListener;

		public GalleryAdapterVisitor(GalleryOverviewModuleInterface listInterface,
				GalleryPageAdapter.GalleryItemClickListener clickListener) {
			mInterface = listInterface;
			mListener = clickListener;
		}

		@Override
		public GalleryPageAdapter execute(DummyNavPage dummyNavItem) {
			return new DummyPageAdapter(mInterface, mListener);
		}
	}
}
