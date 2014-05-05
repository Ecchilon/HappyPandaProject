package com.ecchilon.happypandaproject.navigation;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import com.ecchilon.happypandaproject.navigation.IDrawerItem;
import com.ecchilon.happypandaproject.navigation.NavigationDrawerAdapter;
import com.ecchilon.happypandaproject.navigation.NavigationDrawerFragment;

/**
 * Created by Alex on 6-4-2014.
 */
public class SectionDrawerItem implements IDrawerItem {

	private String mTitle;
	private int mSectionIconResID;

	public SectionDrawerItem(String title, int sectionIconResID){
		mTitle = title;
		mSectionIconResID = sectionIconResID;
	}

	public String getTitle(){
		return mTitle;
	}

	public int getSectionIconResID() {
		return mSectionIconResID;
	}

	@Override
	public <T> T visit(NavigationDrawerAdapter.IDrawerVisitor<T> visitor, View convertView, ViewGroup group) {
		return visitor.execute(this, convertView, group);
	}

	@Override
	public int getViewType() {
		return 0;
	}

	@Override
	public boolean isFragmentDisplay() {
		return false;
	}
}