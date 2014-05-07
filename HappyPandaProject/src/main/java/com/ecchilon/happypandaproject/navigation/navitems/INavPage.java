package com.ecchilon.happypandaproject.navigation.navitems;

import com.ecchilon.happypandaproject.navigation.INavVisitor;

/**
 * Created by Alex on 11-4-2014.
 */
public interface INavPage {
	<T> T visit(INavVisitor<T> visitor);

	String getTitle();
}
