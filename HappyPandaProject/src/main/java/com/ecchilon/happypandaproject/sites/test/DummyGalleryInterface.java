package com.ecchilon.happypandaproject.sites.test;

import android.view.View;

import com.ecchilon.happypandaproject.GalleryItem;

import java.util.ArrayList;
import java.util.List;

import com.ecchilon.happypandaproject.sites.GalleryOverviewInterface;

/**
 * A simply dummy class to test the factory and the fragments
 * Created by Alex on 1/4/14.
 */
public class DummyGalleryInterface implements GalleryOverviewInterface {

    @Override
    public void getPage(int index, GalleryPageCreatedCallback listener) {
        if(index < 3) {
            List<GalleryItem> items = new ArrayList<GalleryItem>();
            for(int i = 0; i < 10; i++)
                items.add(new GalleryItem("TitleTest " + (index*10 + i), null, null));

            listener.GalleryOverviewPageCreated(items);
        }
        else
            listener.PageCreationFailed();
    }

    @Override
    public String getTitle() {
        return "Dummy";
    }

    @Override
    public String getSubTitle() {
        return "test";
    }

    @Override
    public String getInnerLayoutName() {
        return "";
    }

    @Override
    public void setInnerContentView(GalleryItem item, View innerView) {

    }
}