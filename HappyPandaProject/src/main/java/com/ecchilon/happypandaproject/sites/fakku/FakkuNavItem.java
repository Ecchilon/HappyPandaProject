package com.ecchilon.happypandaproject.sites.fakku;

import com.ecchilon.happypandaproject.drawer.INavVisitor;
import com.ecchilon.happypandaproject.gallery.navitems.INavItem;

/**
 * Created by Alex on 22-5-2014.
 */
public class FakkuNavItem implements INavItem {

	private String mName;
	private String mBaseUrl;

	public FakkuNavItem(String name, String baseUrl) {
		mName = name;
		mBaseUrl = baseUrl;
	}

	public String getBaseUrl() {
		return mBaseUrl;
	}


	@Override
	public <T> T visit(INavVisitor<T> visitor) {
		return visitor.execute(this);
	}

	@Override
	public String getTitle() {
		return mName;
	}
}
