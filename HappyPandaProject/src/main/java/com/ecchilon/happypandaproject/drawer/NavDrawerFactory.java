package com.ecchilon.happypandaproject.drawer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.gallery.navitems.FavoritesNavItem;
import com.ecchilon.happypandaproject.gallery.navitems.INavItem;
import com.ecchilon.happypandaproject.gson.GsonDrawerItem;
import com.ecchilon.happypandaproject.sites.fakku.FakkuNavItem;

/**
 * Created by Alex on 11-4-2014.
 */
public class NavDrawerFactory {
	private static final String FAKKU_OVERVIEW_URL = "http://www.fakku.net/page/";

	public static NavigationDrawerAdapter createAdapter(Context context, View.OnClickListener bookmarksClickListener) {
		List<IDrawerItem> navigationItemList = new ArrayList<IDrawerItem>();

		navigationItemList.add(new SectionDrawerItem(context.getString(R.string.section_sites), 0));
		loadFrontPages(navigationItemList, context);

		navigationItemList.add(new SectionDrawerItem(context.getString(R.string.section_my_items), 0));
		navigationItemList.add(
				new NavDrawerItem(context.getString(R.string.page_favorite), new FavoritesNavItem(), true));

		navigationItemList.add(
				new EditableSectionDrawerItem(context.getString(R.string.section_bookmarks), R.drawable.icon_bookmark,
						bookmarksClickListener)
		);
		navigationItemList.addAll(loadBookmarks(context));

		return new NavigationDrawerAdapter(navigationItemList);
	}

	//TODO fill frontpages
	private static void loadFrontPages(List<IDrawerItem> itemList, Context context) {
		String fakkuTitle = context.getString(R.string.fakku_overview_title);
		itemList.add(new NavDrawerItem(fakkuTitle,
				new FakkuNavItem(context.getString(R.string.overview_title), FAKKU_OVERVIEW_URL), true));
	}

	public static List<NavDrawerItem> loadBookmarks(Context context) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		if (!preferences.contains(NavigationDrawerFragment.BOOKMARKS)) {
			return new ArrayList<NavDrawerItem>();
		}

		List<NavDrawerItem> items = GsonDrawerItem.getItems(
				preferences.getString(NavigationDrawerFragment.BOOKMARKS, null));

		return items;
	}

	public static NavDrawerItem createBookmark(INavItem item) {
		return new NavDrawerItem(item.getTitle(), item, false);
	}
}
