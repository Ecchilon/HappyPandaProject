package com.ecchilon.happypandaproject.sites;

/**
 * Created by Alex on 1/23/14.
 */
public interface ImageViewerModuleInterface {
    public interface GalleryImageURLCreatedCallback {
        public void ImageURLCreated(String imageURL);
        public void ImageURLCreationFailed();
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
    public void getImageURL(int index, GalleryImageURLCreatedCallback listener);
}
