package com.ecchilon.happypandaproject.gallery;

import java.util.List;

import android.view.Menu;
import android.view.SubMenu;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.gallery.navitems.INavItem;
import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.imageviewer.IMangaVisitor;
import com.ecchilon.happypandaproject.sites.fakku.FakkuMangaItem;

public class OverflowConstructor {
	private Menu mMenu;
	private MenuConstructor mConstructor;

	public OverflowConstructor() {
		mConstructor = new MenuConstructor();
	}

	public void fillOverflowMenu(Menu menu, IMangaItem item) {
		mMenu = menu;
		item.visit(mConstructor);
	}

	/**
	 * Helper class to more easily construct the overflow menu without exposing unnecessary methods
	 */
	private class MenuConstructor implements IMangaVisitor<Void> {
		@Override
		public Void execute(FakkuMangaItem fakkuMangaItem) {
			fillList(fakkuMangaItem.getArtists(), R.string.artists_item);
			fillList(fakkuMangaItem.getTags(), R.string.tags_item);
			fillItem(fakkuMangaItem.getSeries());

			return null;
		}

		private <T extends INavItem> void fillList(List<T> items, int optionalTitleRes) {
			if (items.size() > 1) {
				SubMenu subMenu = mMenu.addSubMenu(optionalTitleRes);

				for (INavItem item : items) {
					subMenu.add(Menu.NONE, item.hashCode(), Menu.NONE, item.getTitle());
				}
			}
			else if (items.size() == 1) {
				INavItem item = items.get(0);
				mMenu.add(Menu.NONE, item.hashCode(), Menu.NONE, item.getTitle());
			}
		}

		private <T extends INavItem> void fillItem(INavItem item) {
			mMenu.add(Menu.NONE, item.hashCode(), Menu.NONE, item.getTitle());
		}
	}
}
