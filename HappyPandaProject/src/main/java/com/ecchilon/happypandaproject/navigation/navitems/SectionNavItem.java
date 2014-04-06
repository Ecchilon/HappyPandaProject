package com.ecchilon.happypandaproject.navigation.navitems;

import com.ecchilon.happypandaproject.navigation.NavigationDrawerAdapter;

/**
 * Created by Alex on 6-4-2014.
 */
public class SectionNavItem implements NavigationDrawerAdapter.INavItem {

	private String mTitle;

	public SectionNavItem(String title){
		mTitle = title;
	}

	public String getTitle(){
		return mTitle;
	}
	@Override
	public <T> T visit(NavigationDrawerAdapter.INavVisitor<T> visitor) {
		return visitor.execute(this);
	}

	@Override
	public int getViewType() {
		return 0;
	}
}