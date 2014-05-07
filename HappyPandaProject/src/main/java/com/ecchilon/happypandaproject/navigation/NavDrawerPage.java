package com.ecchilon.happypandaproject.navigation;

import android.view.View;
import android.view.ViewGroup;
import com.ecchilon.happypandaproject.navigation.navitems.INavPage;

/**
 * Handles item with an @INavPage in the drawer Created by Alex on 12-4-2014.
 */
public class NavDrawerPage implements IDrawerPage {
	private String mTitle;
	private INavPage mNavItem;
	private boolean mIsFragmentDisplay;

	public NavDrawerPage(String mTitle, INavPage mNavItem, boolean mIsFragmentDisplay) {
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

	public void setTitle(String title) {
		mTitle = title;
	}

	public INavPage getNavItem() {
		return mNavItem;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof NavDrawerPage)) {
			return false;
		}

		NavDrawerPage other = (NavDrawerPage) o;

		return mTitle.equals(other.mTitle) && mNavItem.equals(other.mNavItem)
				&& mIsFragmentDisplay == other.mIsFragmentDisplay;
	}
}
