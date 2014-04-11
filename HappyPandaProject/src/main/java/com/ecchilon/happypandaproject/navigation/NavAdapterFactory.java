package com.ecchilon.happypandaproject.navigation;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.navigation.navitems.INavItem;
import com.ecchilon.happypandaproject.navigation.navitems.SectionItem;

/**
 * Created by Alex on 11-4-2014.
 */
public class NavAdapterFactory {
	public static NavigationDrawerAdapter createAdapter(Context context){
		List<INavItem> navigationItemList = new ArrayList<INavItem>();

		navigationItemList.add(new SectionItem(context.getString(R.string.section_sites), 0));
		loadFrontPages(navigationItemList, context);

		//TODO add gallery bookmarks in the future

		navigationItemList.add(new SectionItem(context.getString(R.string.section_bookmarks), R.drawable.ic_menu_star));
		loadBookmarks(navigationItemList, context);

		return new NavigationDrawerAdapter(navigationItemList);
	}

	//TODO fill frontpages
	private static void loadFrontPages(List<INavItem> itemList, Context context){

	}

	//TODO fill bookmarks if available
	private static void loadBookmarks(List<INavItem> itemList, Context context){

	}
}
