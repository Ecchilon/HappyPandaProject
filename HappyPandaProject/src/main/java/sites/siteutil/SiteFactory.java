package sites.siteutil;

/**
 * Created by Alex on 1/4/14.
 */
public class SiteFactory {
    public GalleryOverviewInterface getSiteInterface(int index) {
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
