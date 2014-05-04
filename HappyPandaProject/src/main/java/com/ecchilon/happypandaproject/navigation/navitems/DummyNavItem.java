package com.ecchilon.happypandaproject.navigation.navitems;

import com.ecchilon.happypandaproject.navigation.INavVisitor;

/**
 * Created by Alex on 5/3/2014.
 */
public class DummyNavItem implements INavItem {

	@Override
	public <T> T visit(INavVisitor<T> visitor) {
		return visitor.execute(this);
	}

	@Override
	public String getTitle() {
		return "Dummy";
	}
}
