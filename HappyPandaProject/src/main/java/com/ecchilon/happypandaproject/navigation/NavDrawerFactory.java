package com.ecchilon.happypandaproject.navigation;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.gson.GsonDrawerItem;
import com.ecchilon.happypandaproject.navigation.navitems.INavPage;
import com.ecchilon.happypandaproject.sites.test.DummyNavPage;

/**
 * Created by Alex on 11-4-2014.
 */
public class NavDrawerFactory {
	public static NavigationDrawerAdapter createAdapter(Context context, View.OnClickListener bookmarksClickListener) {
		List<IDrawerPage> navigationItemList = new ArrayList<IDrawerPage>();

		navigationItemList.add(new SectionDrawerPage(context.getString(R.string.section_sites), 0));
		loadFrontPages(navigationItemList, context);

		navigationItemList.add(
				new EditableSectionDrawerPage(context.getString(R.string.section_bookmarks), R.drawable.bookmark,
						bookmarksClickListener));
		navigationItemList.addAll(loadBookmarks(context));

		return new NavigationDrawerAdapter(navigationItemList);
	}

	//TODO fill frontpages
	private static void loadFrontPages(List<IDrawerPage> itemList, Context context) {
		itemList.add(
				new NavDrawerPage(context.getString(R.string.page_dummy), new DummyNavPage("Dummy Overview"), true));
	}

	public static List<NavDrawerPage> loadBookmarks(Context context) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		if (!preferences.contains(NavigationDrawerFragment.BOOKMARKS)) {
			return new ArrayList<NavDrawerPage>();
		}

		List<NavDrawerPage> items = GsonDrawerItem.getItems(
				preferences.getString(NavigationDrawerFragment.BOOKMARKS, null));

		return items;
	}

	public static NavDrawerPage createBookmark(INavPage item) {
		return new NavDrawerPage(item.getTitle(), item, false);
	}
}
