package com.ecchilon.happypandaproject;

import java.util.Map;
import java.util.Set;

import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.ecchilon.happypandaproject.favorites.FavoritesLoader;
import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.navigation.navitems.INavItem;
import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;
import com.ecchilon.happypandaproject.util.PagedScrollAdapter;

/**
 * Created by Alex on 1/4/14.
 */
public abstract class GalleryPageAdapter<T extends IMangaItem> extends PagedScrollAdapter<T> {

	public interface PageCreationFailedListener {
		public void PageCreationFailed();
	}

	public interface GalleryItemClickListener {
		public void GalleryItemClicked(IMangaItem item);

		public void GalleryNavItemClicked(INavItem item);

		public void GalleryItemFavoriteClicked(IMangaItem item);
	}

	private PageCreationFailedListener mListener;
	private GalleryItemClickListener mGalleryItemClickListener;
	private GalleryOverviewModuleInterface mGalleryInterface;
	private FavoritesLoader mLoader;

	private PopupMenu mCurrentOverflowMenu;

	public GalleryPageAdapter(GalleryOverviewModuleInterface galleryInterface,
			GalleryItemClickListener itemClickListener, FavoritesLoader loader) {
		mGalleryInterface = galleryInterface;
		mGalleryItemClickListener = itemClickListener;
		mLoader = loader;
	}

	protected GalleryOverviewModuleInterface getInterface() {
		return mGalleryInterface;
	}

	protected GalleryItemClickListener getGalleryItemClickListener() {
		return mGalleryItemClickListener;
	}

	public void setPageCreationFailedListener(PageCreationFailedListener _listener) {
		mListener = _listener;
	}

	@Override
	public void loadNewDataSet() {
		mGalleryInterface.getPage(getCurrentPage(), this);
	}

	@Override
	public void PageCreationFailed() {
		if (mListener != null) {
			mListener.PageCreationFailed();
		}
	}

	@Override
	public abstract View getView(int i, View view, ViewGroup viewGroup);

	/**
	 * Shows a new overflow menu anchored to the provided view, with the items as defined in #overflowItems. On click,
	 * the selected INavItem will be passed to the available @GalleryItemClickListener#GalleryNavItemClicked
	 *
	 * @param anchor
	 * @param overflowItems
	 */
	protected void showOverflowView(View anchor, final Map<String, INavItem> overflowItems) {
		closeCurrentOverflowMenu();

		final PopupMenu popupMenu = new PopupMenu(anchor.getContext(), anchor);

		Menu overflowMenu = popupMenu.getMenu();

		fillMenu(overflowMenu, overflowItems.keySet());

		popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
			@Override
			public void onDismiss(PopupMenu popupMenu) {
				if (popupMenu == mCurrentOverflowMenu) {
					mCurrentOverflowMenu = null;
				}
			}
		});

		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem menuItem) {
				String key = menuItem.getTitle().toString();
				if (overflowItems.containsKey(key)) {
					INavItem value = overflowItems.get(key);

					if (mGalleryItemClickListener != null) {
						mGalleryItemClickListener.GalleryNavItemClicked(value);
					}
				}

				closeCurrentOverflowMenu();

				return true;
			}
		});

		mCurrentOverflowMenu = popupMenu;
		mCurrentOverflowMenu.show();
	}

	/**
	 * Fills the menu with set of string from map. since these strings are unique keys, no int id is needed
	 *
	 * @param menu
	 * @param itemNames
	 */
	private void fillMenu(Menu menu, Set<String> itemNames) {
		for (String name : itemNames) {
			menu.add(name);
		}
	}

	/**
	 * Returns whether this item has been added to favorites
	 *
	 * @param item
	 * @return
	 */
	protected final boolean isFavorite(T item) {
		return mLoader.containsFavorite(item);
	}

	/**
	 * Dismisses the menu if it's opened
	 */
	public void closeCurrentOverflowMenu() {
		if (mCurrentOverflowMenu != null) {
			mCurrentOverflowMenu.dismiss();
		}
	}
}
