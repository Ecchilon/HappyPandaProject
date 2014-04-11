package com.ecchilon.happypandaproject.navigation.navitems;

import android.os.Parcel;
import android.os.Parcelable;
import com.ecchilon.happypandaproject.navigation.NavigationDrawerAdapter;

/**
 * Created by Alex on 6-4-2014.
 */
public class OverviewNavItem implements INavItem, Parcelable {

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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(mTitle);
		parcel.writeString(mUrl);
	}

	public static final Parcelable.Creator<OverviewNavItem> CREATOR
			= new Parcelable.Creator<OverviewNavItem>() {
		public OverviewNavItem createFromParcel(Parcel in) {
			return new OverviewNavItem(in);
		}

		public OverviewNavItem[] newArray(int size) {
			return new OverviewNavItem[size];
		}
	};

	private OverviewNavItem(Parcel in) {
		mTitle = in.readString();
		mUrl  = in.readString();
	}
}
