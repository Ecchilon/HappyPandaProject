package sites;
import java.util.List;

/**
 * Created by Alex on 1/4/14.
 */
public interface GalleryOverviewInterface {
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
}
