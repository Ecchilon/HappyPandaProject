package com.ecchilon.happypandaproject.navigation.navitems;

import com.ecchilon.happypandaproject.navigation.INavVisitor;

/**
 * Navigation Item for the user's favorites Created by Alex on 9-5-2014.
 */
public class FavoritesNavItem implements INavItem {
	@Override
	public <T> T visit(INavVisitor<T> visitor) {
		return visitor.execute(this);
	}

	@Override
	public String getTitle() {
		return "Favorites";
	}
}
