package com.ecchilon.happypandaproject.gallery.navitems;

import com.ecchilon.happypandaproject.drawer.INavVisitor;

/**
 * Created by Alex on 14-5-2014.
 */
public class LibraryNavPage extends MyThingsPage {
	@Override
	public <T> T visit(INavVisitor<T> visitor) {
		return visitor.execute(this);
	}

	@Override
	public String getTitle() {
		return "My Library";
	}
}
