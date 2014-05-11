package com.ecchilon.happypandaproject.sites.test;

import com.ecchilon.happypandaproject.drawer.INavVisitor;
import com.ecchilon.happypandaproject.gallery.navitems.INavItem;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof DummyNavItem)) {
			return false;
		}

		DummyNavItem other = (DummyNavItem) o;

		return mDummyTitle.equals(other.mDummyTitle);
	}
}
