package com.ecchilon.happypandaproject.sites;

import android.graphics.Bitmap;

/**
 * Created by Alex on 1/23/14.
 */
public interface AlbumPagesModuleInterface {
    public interface GalleryImageCreatedCallback {
        public void ImageBitmapCreated(Bitmap bitmap);
        public void ImageURLCreated(String imageURL);
        public void ImageCreationFailed();
    }

    /**
     * @return the number of pages in the current gallery.
     */
    public int getPageCount();

    /**
     * Requests the image url through a listener. Handled through listener to allow module to load
     * next page asynchronously.
     * @param index The 0-based index of the requested image page in the currently viewed gallery.
     * @param listener the listener with the callbacks for the url request.
     */
    public void getImage(int index, GalleryImageCreatedCallback listener);
}
