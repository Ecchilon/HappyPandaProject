package com.ecchilon.happypandaproject;

import com.google.gson.Gson;

/**
 * Created by Alex on 1/4/14.
 */
public class AlbumItem{

    protected String mTitle;
    protected String mThumbUrl;
    protected String mAlbumUrl;

    protected boolean mIsStored;
    protected boolean mIsFavorite;

    public AlbumItem(String mTitle, String mThumbUrl, String mAlbumUrl, boolean mIsStored, boolean mIsFavorite) {
        this.mTitle = mTitle;
        this.mThumbUrl = mThumbUrl;
        this.mAlbumUrl = mAlbumUrl;
        this.mIsStored = mIsStored;
        this.mIsFavorite = mIsFavorite;
    }

    public boolean getIsStored() { return mIsStored; }
    public void setIsStored(boolean isStored) { mIsStored = isStored; }

    public boolean getIsFavorite() { return mIsFavorite; }
    public void setIsFavorite(boolean isFavorite) { mIsFavorite = isFavorite; }

    public String getAlbumUrl() {
        return mAlbumUrl;
    }

    public String getThumbUrl() {
        return mThumbUrl;
    }

    public String getTitle(){
        return mTitle;
    }

    public String toJSONString() {
        Gson gson = new Gson();
        return gson.toJson(this, AlbumItem.class);
    }

    public static AlbumItem fromJSONString(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, AlbumItem.class);
    }
}
