package com.ecchilon.happypandaproject.sites.util;

import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;
import com.ecchilon.happypandaproject.sites.test.DummyGalleryModuleInterface;
import com.ecchilon.happypandaproject.sites.test.DummySearchModuleInterface;

/**
 * Created by Alex on 1/4/14.
 */
public class SiteFactory {
    public static GalleryOverviewModuleInterface getOverviewInterface(int index) {
        switch (index)
        {
            case 0:
                return new DummyGalleryModuleInterface();
            case 1:
                return new DummyGalleryModuleInterface();
            case 2:
                return new DummyGalleryModuleInterface();
            default:
                return null;
        }
    }

    public static GalleryOverviewModuleInterface getSearchInterface(int index, String query) {
        switch (index)
        {
            case 0:
                return new DummySearchModuleInterface(query);
            case 1:
                return new DummySearchModuleInterface(query);
            case 2:
                return new DummySearchModuleInterface(query);
            default:
                return null;
        }
    }
}
