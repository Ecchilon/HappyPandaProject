package com.ecchilon.happypandaproject.sites.test;

import com.ecchilon.happypandaproject.navigation.INavVisitor;
import com.ecchilon.happypandaproject.navigation.navitems.INavItem;

/**
 * Created by Alex on 5/3/2014.
 */
public class DummyNavItem implements INavItem {

	private String mDummyTitle;

	public DummyNavItem(String title) {
		mDummyTitle = title;
	}

	@Override
	public <T> T visit(INavVisitor<T> visitor) {
		return visitor.execute(this);
	}

	@Override
	public String getTitle() {
		return mDummyTitle;
	}
}
