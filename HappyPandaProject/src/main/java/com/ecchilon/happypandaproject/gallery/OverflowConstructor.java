package com.ecchilon.happypandaproject.gallery;

import java.util.List;

import android.content.Context;
import android.view.Menu;
import android.view.SubMenu;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.gallery.navitems.INavItem;
import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.imageviewer.IMangaVisitor;
import com.ecchilon.happypandaproject.sites.fakku.FakkuManga;

public class OverflowConstructor {
	private Menu mMenu;
	private MenuConstructor mConstructor;
	private Context mAppContext;

	public OverflowConstructor(Context context) {
		mConstructor = new MenuConstructor();
		mAppContext = context.getApplicationContext();
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
		public Void execute(FakkuManga fakkuManga) {
			fillList(fakkuManga.getArtists(), R.string.artists_item, R.string.artist_item);
			fillItem(fakkuManga.getSeries(), R.string.series_item);
			fillList(fakkuManga.getTags(), R.string.tags_item, R.string.tag_item);

			return null;
		}

		private <T extends INavItem> void fillList(List<T> items, int titleRes, int prefixRes) {
			if (items.size() > 1) {
				SubMenu subMenu = mMenu.addSubMenu(titleRes);

				for (INavItem item : items) {
					subMenu.add(Menu.NONE, item.hashCode(), Menu.NONE, item.getTitle());
				}
			}
			else if (items.size() == 1) {
				INavItem item = items.get(0);
				mMenu.add(Menu.NONE, item.hashCode(), Menu.NONE,
						mAppContext.getString(prefixRes) + " " + item.getTitle());
			}
		}

		private <T extends INavItem> void fillItem(INavItem item, int prefixRes) {
			mMenu.add(Menu.NONE, item.hashCode(), Menu.NONE, mAppContext.getString(prefixRes) + " " + item.getTitle());
		}
	}
}
