package com.ecchilon.happypandaproject.imageviewer;

import com.google.gson.Gson;

/**
 * Created by Alex on 1/4/14.
 */
public class ImageViewerItem {

    protected String mTitle;
    protected String mThumbUrl;
    protected String mGalleryUrl;

    public ImageViewerItem(String mTitle, String mThumbUrl, String mGalleryUrl) {
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
        return gson.toJson(this, ImageViewerItem.class);
    }

    public static ImageViewerItem fromJSONString(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, ImageViewerItem.class);
    }
}
