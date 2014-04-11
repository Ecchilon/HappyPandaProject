package com.ecchilon.happypandaproject.navigation.navitems;

import com.ecchilon.happypandaproject.navigation.NavigationDrawerAdapter;

/**
 * Created by Alex on 11-4-2014.
 */
public interface INavItem {
	<T> T visit(NavigationDrawerAdapter.INavVisitor<T> visitor);
	int getViewType();
}
