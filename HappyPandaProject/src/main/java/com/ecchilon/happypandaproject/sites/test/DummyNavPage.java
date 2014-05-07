package com.ecchilon.happypandaproject.sites.test;

import com.ecchilon.happypandaproject.navigation.INavVisitor;
import com.ecchilon.happypandaproject.navigation.navitems.INavPage;

/**
 * Created by Alex on 5/3/2014.
 */
public class DummyNavPage implements INavPage {

	private String mDummyTitle;

	public DummyNavPage(String title) {
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

		if (!(o instanceof DummyNavPage)) {
			return false;
		}

		DummyNavPage other = (DummyNavPage) o;

		return mDummyTitle.equals(other.mDummyTitle);
	}
}
