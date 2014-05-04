package com.ecchilon.happypandaproject.sites.test;

import android.view.View;

import com.ecchilon.happypandaproject.imageviewer.ImageViewerItem;
import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * A simply dummy class to test the factory and the fragments
 * Created by Alex on 1/4/14.
 */
public class DummyGalleryModuleInterface implements GalleryOverviewModuleInterface {

    @Override
    public void getPage(int index, GalleryPageCreatedCallback listener) {
        if(index < 3) {
            List<ImageViewerItem> items = new ArrayList<ImageViewerItem>();
            for(int i = 0; i < 10; i++)
                items.add(new ImageViewerItem("TitleTest " + (index*10 + i), null, null));

            listener.GalleryOverviewPageCreated(items);
        }
        else
            listener.PageCreationFailed();
    }

    @Override
    public String getSubTitle() {
        return "test";
    }
}
