package sites.util;

import sites.DummyGalleryInterface;

/**
 * Created by Alex on 1/4/14.
 */
public class SiteFactory {
    public static GalleryOverviewInterface getOverviewInterface(int index) {
        switch (index)
        {
            case 0:
                return new DummyGalleryInterface();
            case 1:
                return new DummyGalleryInterface();
            case 2:
                return new DummyGalleryInterface();
            default:
                return null;
        }
    }

    public static GalleryOverviewInterface getSearchInterface(int index) {
        switch (index)
        {
            case 0:
                return new DummyGalleryInterface();
            case 1:
                return new DummyGalleryInterface();
            case 2:
                return new DummyGalleryInterface();
            default:
                return null;
        }
    }
}
