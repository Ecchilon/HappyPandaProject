package com.ecchilon.happypandaproject.drawer;

import com.ecchilon.happypandaproject.gallery.navitems.FavoritesNavItem;
import com.ecchilon.happypandaproject.gallery.navitems.LibraryNavPage;
import com.ecchilon.happypandaproject.sites.test.DummyNavItem;

/**
 * Created by Alex on 12-4-2014.
 */
public interface INavVisitor<T> {
	T execute(DummyNavItem dummyNavItem);

	T execute(FavoritesNavItem favoritesNavItem);

	T execute(LibraryNavPage libraryNavPage);
}

