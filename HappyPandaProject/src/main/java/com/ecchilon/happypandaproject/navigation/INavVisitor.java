package com.ecchilon.happypandaproject.navigation;

import com.ecchilon.happypandaproject.navigation.navitems.DummyNavItem;

/**
 * Created by Alex on 12-4-2014.
 */
public interface INavVisitor<T> {
	T execute(DummyNavItem dummyNavItem);
}

