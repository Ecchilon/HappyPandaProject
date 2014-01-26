package com.ecchilon.happypandaproject;

import android.os.Parcel;
import android.os.Parcelable;

import com.ecchilon.happypandaproject.favorites.FavoritesDatabaseHelper;
import com.ecchilon.happypandaproject.storage.StorageController;

/**
 * Created by Alex on 1/4/14.
 */
public class GalleryItem implements Parcelable{

    protected String mTitle;
    protected String mThumbUrl;
    protected String mGalleryUrl;

    protected boolean mIsStored;
    protected boolean mIsFavorite;

    public boolean getIsStored() { return mIsStored; }

    public boolean getIsFavorite() { return mIsFavorite; }

    public String getGalleryUrl() {
        return mGalleryUrl;
    }

    public String getThumbUrl() {
        return mThumbUrl;
    }

    public String getTitle(){
        return mTitle;
    }

    public GalleryItem(String mTitle, String mThumbUrl, String mGalleryUrl) {
        this.mTitle = mTitle;
        this.mThumbUrl = mThumbUrl;
        this.mGalleryUrl = mGalleryUrl;

        mIsFavorite = FavoritesDatabaseHelper.getInstance().getFavorite(mGalleryUrl) != null;
        mIsStored = StorageController.isStored(this);
    }

    public static final Creator<GalleryItem> CREATOR = new  Creator<GalleryItem>() {
        @Override
        public GalleryItem createFromParcel(Parcel parcel) {
            return new GalleryItem(parcel);
        }

        @Override
        public GalleryItem[] newArray(int size) {
            return new GalleryItem[size];
        }
    };

    public GalleryItem(Parcel parcel) {
        mTitle = parcel.readString();
        mThumbUrl = parcel.readString();
        mGalleryUrl = parcel.readString();
        mIsStored = parcel.readByte() != 0;
        mIsFavorite = parcel.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mThumbUrl);
        parcel.writeString(mGalleryUrl);
        parcel.writeByte((byte)(mIsStored? 1 : 0));
        parcel.writeByte((byte)(mIsFavorite? 1 : 0));
    }
}
