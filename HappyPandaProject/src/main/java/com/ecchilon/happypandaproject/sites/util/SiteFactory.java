package com.ecchilon.happypandaproject.sites.util;

import com.ecchilon.happypandaproject.GalleryAdapter;
import com.ecchilon.happypandaproject.imageviewer.ImageViewerItem;
import com.ecchilon.happypandaproject.navigation.INavVisitor;
import com.ecchilon.happypandaproject.sites.test.DummyAdapter;
import com.ecchilon.happypandaproject.sites.test.DummyNavItem;
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
	/**
	 * Returns the site interface based on the passed INavItem
	 *
	 * @param index
	 * @return
	 */
	public static GalleryOverviewModuleInterface getOverviewInterface(INavItem index) {
		return index.visit(new SiteNavVisitor());
	}

	/**
	 * Returns the menu_search interface based on the passed INavItem and String
	 *
	 * @param index
	 * @param query
	 * @return
	 */
	public static GalleryOverviewModuleInterface getSearchInterface(INavItem index, String query) {
		return index.visit(new SearchNavVisitor(query));
	}

	/**
	 * Returns a new @GalleryAdapter to use in the gallery view
	 *
	 * @param item
	 * @param listener
	 * @return
	 */
	public static GalleryAdapter getGalleryAdapter(INavItem item, GalleryOverviewModuleInterface listInterface,
			GalleryAdapter.GalleryItemClickListener listener) {
		return item.visit(new GalleryAdapterVisitor(listInterface, listener));
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

	private static class SearchNavVisitor implements INavVisitor<GalleryOverviewModuleInterface> {

		private String mQuery;

		public SearchNavVisitor(String query) {
			mQuery = query;
		}

		@Override
		public GalleryOverviewModuleInterface execute(DummyNavItem dummyNavItem) {
			return new DummySearchModuleInterface(mQuery);
		}
	}

	private static class GalleryAdapterVisitor implements INavVisitor<GalleryAdapter> {
		private GalleryOverviewModuleInterface mInterface;
		private GalleryAdapter.GalleryItemClickListener mListener;

		public GalleryAdapterVisitor(GalleryOverviewModuleInterface listInterface,
				GalleryAdapter.GalleryItemClickListener clickListener) {
			mInterface = listInterface;
			mListener = clickListener;
		}

		@Override
		public GalleryAdapter execute(DummyNavItem dummyNavItem) {
			return new DummyAdapter(mInterface, mListener);
		}
	}
}
