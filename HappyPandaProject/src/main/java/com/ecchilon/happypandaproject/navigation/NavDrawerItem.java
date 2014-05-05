package com.ecchilon.happypandaproject.navigation;

import android.view.View;
import android.view.ViewGroup;
import com.ecchilon.happypandaproject.navigation.navitems.INavItem;

/**
 * Created by Alex on 12-4-2014.
 */
public class NavDrawerItem implements IDrawerItem {
	private String mTitle;
	private INavItem mNavItem;
	private boolean mIsFragmentDisplay;

	public NavDrawerItem(String mTitle, INavItem mNavItem, boolean mIsFragmentDisplay) {
		this.mTitle = mTitle;
		this.mNavItem = mNavItem;
		this.mIsFragmentDisplay = mIsFragmentDisplay;
	}

	@Override
	public <T> T visit(NavigationDrawerAdapter.IDrawerVisitor<T> visitor, View convertView, ViewGroup group) {
		return visitor.execute(this, convertView, group);
	}

	@Override
	public int getViewType() {
		return 1;
	}

	@Override
	public boolean isFragmentDisplay() {
		return mIsFragmentDisplay;
	}

	public String getTitle() {
		return mTitle;
	}

	public INavItem getNavItem() {
		return mNavItem;
	}
}
