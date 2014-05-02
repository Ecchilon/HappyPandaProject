package com.ecchilon.happypandaproject.navigation;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.navigation.navitems.OverviewNavItem;

/**
 * Created by Alex on 11-4-2014.
 */
public class NavAdapterFactory {
	public static NavigationDrawerAdapter createAdapter(Context context){
		List<IDrawerItem> navigationItemList = new ArrayList<IDrawerItem>();

		navigationItemList.add(new SectionDrawerItem(context.getString(R.string.section_sites), 0));
		loadFrontPages(navigationItemList, context);

		//TODO add gallery bookmarks in the future

		navigationItemList.add(new SectionDrawerItem(context.getString(R.string.section_bookmarks), R.drawable.ic_menu_star));
		loadBookmarks(navigationItemList, context);

		return new NavigationDrawerAdapter(navigationItemList);
	}

	//TODO fill frontpages
	private static void loadFrontPages(List<IDrawerItem> itemList, Context context){
		itemList.add(new NavDrawerItem(context.getString(R.string.page_dummy), new OverviewNavItem(null, null)));
	}

	//TODO fill bookmarks if available
	private static void loadBookmarks(List<IDrawerItem> itemList, Context context){

	}
}
