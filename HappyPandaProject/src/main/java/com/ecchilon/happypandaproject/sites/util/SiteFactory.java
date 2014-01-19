package com.ecchilon.happypandaproject.sites.util;

import com.ecchilon.happypandaproject.sites.GalleryOverviewInterface;
import com.ecchilon.happypandaproject.sites.test.DummyGalleryInterface;
import com.ecchilon.happypandaproject.sites.test.DummySearchInterface;

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

    public static GalleryOverviewInterface getSearchInterface(int index, String query) {
        switch (index)
        {
            case 0:
                return new DummySearchInterface(query);
            case 1:
                return new DummySearchInterface(query);
            case 2:
                return new DummySearchInterface(query);
            default:
                return null;
        }
    }
}
