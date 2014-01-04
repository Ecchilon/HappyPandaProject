package sites.siteinterface;

import com.ecchilon.happypandaproject.GalleryItem;

import java.util.ArrayList;

/**
 * Created by Alex on 1/4/14.
 */
public interface GalleryOverviewInterface {
    public interface GalleryPageCreatedCallback {
        public void GalleryOverviewPageCreated(ArrayList<GalleryItem> pageItems);
        public void PageCreationFailed();
    }

    /**
     * Get the next page of the overview, if available. If not available, PageCreationFailed() should be called on the listener
     * @param currentIndex Index of the application's current page. First request will start at 0.
     * @param listener Listener to pass the constructed list of items to.
     */
    public void nextPage(int currentIndex, GalleryPageCreatedCallback listener);
}
