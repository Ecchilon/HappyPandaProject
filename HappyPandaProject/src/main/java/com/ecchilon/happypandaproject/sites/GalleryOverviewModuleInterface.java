package com.ecchilon.happypandaproject.sites;
import android.view.View;

import com.ecchilon.happypandaproject.GalleryItem;

import java.util.List;

/**
 * Created by Alex on 1/4/14.
 */
public interface GalleryOverviewModuleInterface {
    public interface GalleryPageCreatedCallback<T> {
        public void GalleryOverviewPageCreated(List<T> pageItems);
        public void PageCreationFailed();
    }

    /**
     * Get the next page of the overview, if available. If not available, PageCreationFailed() should be called on the listener
     * @param index Index of the application's current page. First request will start at 0.
     * @param listener Listener to pass the constructed list of items to.
     */
    public void getPage(int index, GalleryPageCreatedCallback listener);

    /**
     * @return The title text to be displayed in the actionbar
     */
    public String getTitle();

    /**
     * @return The subtitle text to be displayed in the actionbar below the title
     */
    public String getSubTitle();

    /**
     * Gets the resource filename of the inner view. Gets queried once during the setup of an adapter.
     * @return The resource ID of the inner view layout.
     */
    public String getInnerLayoutName();

    /**
     * Implement this function to fill the inner view of a card. Title, thumbnail,
     * and buttons are handled by application. Put all other information (tags, author, date, etc.)
     * inside this view.
     * @param item The {@link com.ecchilon.happypandaproject.GalleryItem} that is currently on display.
     * @param innerView The inner view. Is structured as the layout defined in {@link #getInnerLayoutName()}.
     * @return
     */
    public void setCardInnerContentView(GalleryItem item, View innerView);

    //TODO ordering of overview
}
