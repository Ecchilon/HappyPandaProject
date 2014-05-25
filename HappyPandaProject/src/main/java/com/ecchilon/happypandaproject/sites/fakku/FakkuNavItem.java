package com.ecchilon.happypandaproject.sites.fakku;

import com.ecchilon.happypandaproject.drawer.INavVisitor;
import com.ecchilon.happypandaproject.gallery.navitems.INavItem;

/**
 * Created by Alex on 22-5-2014.
 */
public class FakkuNavItem implements INavItem {

	private String mName;
	private String mUrl;

	public FakkuNavItem(String name, String baseUrl) {
		mName = name;
		mUrl = baseUrl;
	}

	public String getUrl() {
		return mUrl;
	}


	@Override
	public <T> T visit(INavVisitor<T> visitor) {
		return visitor.execute(this);
	}

	@Override
	public String getTitle() {
		return mName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof FakkuNavItem)) {
			return false;
		}

		FakkuNavItem other = (FakkuNavItem) o;

		return other.mName.equals(mName) && other.mUrl.equals(mUrl);
	}

	@Override
	public int hashCode() {
		return mName.hashCode() ^ mUrl.hashCode();
	}
}
