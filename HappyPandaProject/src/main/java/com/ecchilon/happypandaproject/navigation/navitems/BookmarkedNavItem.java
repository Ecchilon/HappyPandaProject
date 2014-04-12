package com.ecchilon.happypandaproject.navigation.navitems;

import android.os.Parcel;
import android.os.Parcelable;
import com.ecchilon.happypandaproject.navigation.INavVisitor;
import com.ecchilon.happypandaproject.navigation.NavigationDrawerAdapter;

/**
 * Created by Alex on 6-4-2014.
 */
public class BookmarkedNavItem implements INavItem {

	private String mTitle;
	private String mUrl;

	public BookmarkedNavItem(String title, String url){
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
	public <T> T visit(INavVisitor<T> visitor) {
		return visitor.execute(this);
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

	public static final Parcelable.Creator<BookmarkedNavItem> CREATOR
			= new Parcelable.Creator<BookmarkedNavItem>() {
		public BookmarkedNavItem createFromParcel(Parcel in) {
			return new BookmarkedNavItem(in);
		}

		public BookmarkedNavItem[] newArray(int size) {
			return new BookmarkedNavItem[size];
		}
	};

	private BookmarkedNavItem(Parcel in) {
		mTitle = in.readString();
		mUrl  = in.readString();
	}
}