package com.ecchilon.happypandaproject.sites;

import java.util.List;

import com.ecchilon.happypandaproject.imageviewer.IMangaItem;

/**
 * Created by Alex on 1/4/14.
 */
public interface GalleryOverviewModuleInterface<T extends IMangaItem> {
	public interface GalleryPageCreatedCallback<T> {
		public void GalleryOverviewPageCreated(List<T> pageItems);

		public void PageCreationFailed();
	}

	/**
	 * Get the next page of the overview, if available. If not available, PageCreationFailed() should be called on the
	 * listener
	 *
	 * @param index    Index of the application's current page. First request will start at 0.
	 * @param listener Listener to pass the constructed list of items to.
	 */
	public void getPage(int index, GalleryPageCreatedCallback<T> listener);
}
