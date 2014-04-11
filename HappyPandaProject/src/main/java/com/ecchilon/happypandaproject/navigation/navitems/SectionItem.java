package com.ecchilon.happypandaproject.navigation.navitems;

import android.os.Parcel;
import android.os.Parcelable;
import com.ecchilon.happypandaproject.navigation.NavigationDrawerAdapter;

/**
 * Created by Alex on 6-4-2014.
 */
public class SectionItem implements INavItem {

	private String mTitle;
	private int mSectionIconResID;

	public SectionItem(String title, int sectionIconResID){
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
	public <T> T visit(NavigationDrawerAdapter.INavVisitor<T> visitor) {
		return visitor.execute(this);
	}

	@Override
	public int getViewType() {
		return 0;
	}
}