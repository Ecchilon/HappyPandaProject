package com.ecchilon.happypandaproject.gallery;

import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import com.ecchilon.happypandaproject.gallery.navitems.INavItem;
import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.imageviewer.IMangaVisitor;
import com.ecchilon.happypandaproject.sites.fakku.FakkuManga;
import com.ecchilon.happypandaproject.sites.fakku.FakkuNavItem;

public class OverFlowClickListener implements PopupMenu.OnMenuItemClickListener {

	public interface ClickListenerCallback {
		public AbstractGalleryPageAdapter.GalleryItemClickListener getClickListener();
	}

	private IMangaItem mOverflowManga;
	private ClickListenerCallback mCallback;
	private MenuItemFinder mItemFinder;

	private int clickedItem;

	public OverFlowClickListener(IMangaItem manga, ClickListenerCallback callback) {
		mOverflowManga = manga;
		mCallback = callback;

		mItemFinder = new MenuItemFinder();
	}

	@Override
	public boolean onMenuItemClick(MenuItem menuItem) {
		clickedItem = menuItem.getItemId();

		AbstractGalleryPageAdapter.GalleryItemClickListener clickListener = mCallback.getClickListener();

		INavItem item = mOverflowManga.visit(mItemFinder);

		if (item != null && clickListener != null) {
			clickListener.galleryNavItemClicked(item);
		}

		return true;
	}

	private class MenuItemFinder implements IMangaVisitor<INavItem> {

		@Override
		public INavItem execute(FakkuManga fakkuManga) {
			//TODO find items

			if (fakkuManga.getSeries().hashCode() == clickedItem) {
				return fakkuManga.getSeries();
			}

			for (FakkuNavItem artist : fakkuManga.getArtists()) {
				if (artist.hashCode() == clickedItem) {
					return artist;
				}
			}

			for (FakkuNavItem tag : fakkuManga.getTags()) {
				if (tag.hashCode() == clickedItem) {
					return tag;
				}
			}

			return null;
		}
	}
}
