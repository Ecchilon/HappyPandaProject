package com.ecchilon.happypandaproject.drawer;

import android.view.View;
import android.view.ViewGroup;
import com.ecchilon.happypandaproject.gallery.navitems.INavItem;

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

	public void setTitle(String title) {
		mTitle = title;
	}

	public INavItem getNavItem() {
		return mNavItem;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof NavDrawerItem)) {
			return false;
		}

		NavDrawerItem other = (NavDrawerItem) o;

		return mTitle.equals(other.mTitle) && mNavItem.equals(other.mNavItem)
				&& mIsFragmentDisplay == other.mIsFragmentDisplay;
	}
}
