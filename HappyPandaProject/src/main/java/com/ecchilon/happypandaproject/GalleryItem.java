package com.ecchilon.happypandaproject;

/**
 * Created by Alex on 1/4/14.
 */
public class GalleryItem {

    protected String mTitle;
    protected String mThumbUrl;
    protected String mGalleryUrl;

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
    }
}
