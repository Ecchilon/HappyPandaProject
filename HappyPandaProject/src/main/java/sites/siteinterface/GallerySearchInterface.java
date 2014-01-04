package sites.siteinterface;

/**
 * Created by Alex on 1/4/14.
 */
public interface GallerySearchInterface {
    /**
     * Get the next page of the search, if available. If not available, PageCreationFailed() should be called on the listener
     * @param query the entered query.
     * @param currentIndex Index of the application's current page. First request will start at 0.
     * @param listener Listener to pass the constructed list of items to.
     */
    public void NextPage(String query, int currentIndex, GalleryOverviewInterface.GalleryPageCreatedCallback listener);
}
