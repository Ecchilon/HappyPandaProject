package com.ecchilon.happypandaproject.navigation;

import com.ecchilon.happypandaproject.navigation.navitems.BookmarkedNavItem;
import com.ecchilon.happypandaproject.navigation.navitems.OverviewNavItem;

/**
 * Created by Alex on 12-4-2014.
 */
public interface INavVisitor<T> {
	T execute(BookmarkedNavItem simpleNavItem);
	T execute(OverviewNavItem overviewNavItem);
}

