package com.ecchilon.happypandaproject;

import com.google.gson.Gson;

/**
 * Created by Alex on 1/4/14.
 */
public class GalleryItem {

    protected String mTitle;
    protected String mThumbUrl;
    protected String mGalleryUrl;

    public GalleryItem(String mTitle, String mThumbUrl, String mGalleryUrl) {
        this.mTitle = mTitle;
        this.mThumbUrl = mThumbUrl;
        this.mGalleryUrl = mGalleryUrl;
    }
    public String getGalleryUrl() {
        return mGalleryUrl;
    }

    public String getThumbUrl() {
        return mThumbUrl;
    }

    public String getTitle(){
        return mTitle;
    }

    public String toJSONString() {
        Gson gson = new Gson();
        return gson.toJson(this, GalleryItem.class);
    }

    public static GalleryItem fromJSONString(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, GalleryItem.class);
    }
}
