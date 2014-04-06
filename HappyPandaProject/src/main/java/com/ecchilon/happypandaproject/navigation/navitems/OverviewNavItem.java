package com.ecchilon.happypandaproject.navigation.navitems;

import com.ecchilon.happypandaproject.navigation.NavigationDrawerAdapter;

/**
 * Created by Alex on 6-4-2014.
 */
public class OverviewNavItem implements NavigationDrawerAdapter.INavItem {

	private String mTitle;
	private String mUrl;

	public OverviewNavItem(String title, String url){
		mTitle = title;
		mUrl = url;
	}

	public String getTitle(){
		return mTitle;
	}

	public String getUrl(){
		return mUrl;
	}

	@Override
	public <T> T visit(NavigationDrawerAdapter.INavVisitor<T> visitor) {
		return visitor.execute(this);
	}

	@Override
	public int getViewType() {
		return 2;
	}
}
