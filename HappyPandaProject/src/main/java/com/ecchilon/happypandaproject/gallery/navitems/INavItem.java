package com.ecchilon.happypandaproject.gallery.navitems;

import com.ecchilon.happypandaproject.drawer.INavVisitor;

/**
 * Created by Alex on 11-4-2014.
 */
public interface INavItem {
	<T> T visit(INavVisitor<T> visitor);

	String getTitle();
}
