package com.ecchilon.happypandaproject.navigation;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.sites.test.DummyNavItem;
import com.ecchilon.happypandaproject.gson.GsonNavItem;
import com.ecchilon.happypandaproject.navigation.navitems.INavItem;

/**
 * Created by Alex on 11-4-2014.
 */
public class NavAdapterFactory {
	public static NavigationDrawerAdapter createAdapter(Context context) {
		List<IDrawerItem> navigationItemList = new ArrayList<IDrawerItem>();

		navigationItemList.add(new SectionDrawerItem(context.getString(R.string.section_sites), 0));
		loadFrontPages(navigationItemList, context);

		navigationItemList.add(
				new SectionDrawerItem(context.getString(R.string.section_bookmarks), R.drawable.ic_menu_star));
		loadBookmarks(navigationItemList, context);

		return new NavigationDrawerAdapter(navigationItemList);
	}

	//TODO fill frontpages
	private static void loadFrontPages(List<IDrawerItem> itemList, Context context) {
		itemList.add(new NavDrawerItem(context.getString(R.string.page_dummy), new DummyNavItem(), true));
	}

	private static void loadBookmarks(List<IDrawerItem> itemList, Context context) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		if (!preferences.contains(NavigationDrawerFragment.BOOKMARKS)) {
			return;
		}

		List<INavItem> iNavItems =
				GsonNavItem.getItems(preferences.getString(NavigationDrawerFragment.BOOKMARKS, null));

		for (INavItem item : iNavItems) {
			NavDrawerItem drawerItem = new NavDrawerItem(item.getTitle(), item, false);
			itemList.add(drawerItem);
		}
	}
}
