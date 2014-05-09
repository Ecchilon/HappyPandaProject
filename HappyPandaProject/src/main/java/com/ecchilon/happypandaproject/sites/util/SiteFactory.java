package com.ecchilon.happypandaproject.sites.util;

import com.ecchilon.happypandaproject.GalleryPageAdapter;
import com.ecchilon.happypandaproject.imageviewer.ImageViewerItem;
import com.ecchilon.happypandaproject.navigation.INavVisitor;
import com.ecchilon.happypandaproject.sites.test.DummyPageAdapter;
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
	public static INavItem getSearchInterface(INavItem index, String query) {
		return index.visit(new SearchNavVisitor(query));
	}

	/**
	 * Returns a new @GalleryPageAdapter to use in the gallery view
	 *
	 * @param item
	 * @param listener
	 * @return
	 */
	public static GalleryPageAdapter getGalleryAdapter(INavItem item, GalleryOverviewModuleInterface listInterface,
			GalleryPageAdapter.GalleryItemClickListener listener) {
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

	private static class SearchNavVisitor implements INavVisitor<INavItem> {

		private String mQuery;

		public SearchNavVisitor(String query) {
			mQuery = query;
		}

		@Override
		public INavItem execute(DummyNavItem dummyNavItem) {
			return new DummyNavItem(mQuery);
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
		public GalleryPageAdapter execute(DummyNavItem dummyNavItem) {
			return new DummyPageAdapter(mInterface, mListener);
		}
	}
}
