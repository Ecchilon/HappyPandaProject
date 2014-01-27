package com.ecchilon.happypandaproject.sites.util;

import com.ecchilon.happypandaproject.AlbumItem;
import com.ecchilon.happypandaproject.sites.AlbumOverviewModuleInterface;
import com.ecchilon.happypandaproject.sites.AlbumPagesModuleInterface;
import com.ecchilon.happypandaproject.sites.test.DummyAlbumModuleInterface;
import com.ecchilon.happypandaproject.sites.test.DummyImageModuleInterface;
import com.ecchilon.happypandaproject.sites.test.DummySearchModuleInterface;

/**
 * Created by Alex on 1/4/14.
 */
public class SiteFactory {
    public static AlbumOverviewModuleInterface getOverviewInterface(int index) {
        switch (index)
        {
            case 0:
                return new DummyAlbumModuleInterface();
            case 1:
                return new DummyAlbumModuleInterface();
            case 2:
                return new DummyAlbumModuleInterface();
            default:
                return null;
        }
    }

    public static AlbumOverviewModuleInterface getSearchInterface(int index, String query) {
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

    public static AlbumPagesModuleInterface getGalleryPagesInterface(AlbumItem item) {
        return new DummyImageModuleInterface();
    }
}
