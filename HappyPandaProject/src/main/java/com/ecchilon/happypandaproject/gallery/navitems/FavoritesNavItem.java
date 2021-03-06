package com.ecchilon.happypandaproject.gallery.navitems;

import com.ecchilon.happypandaproject.drawer.INavVisitor;

/**
 * Navigation Item for the user's favorites Created by Alex on 9-5-2014.
 */
public class FavoritesNavItem extends MyThingsPage {
	@Override
	public <T> T visit(INavVisitor<T> visitor) {
		return visitor.execute(this);
	}

	@Override
	public String getTitle() {
		return "Favorites";
	}
}
