package com.ecchilon.happypandaproject.sites.test;

import android.view.View;

import com.ecchilon.happypandaproject.GalleryItem;
import com.ecchilon.happypandaproject.sites.SearchAbstract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 1/17/14.
 */
public class DummySearchModuleInterface extends SearchAbstract {

    public DummySearchModuleInterface(String query) {
        super(query);
    }

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
    public String getUrl(int index) {
        return null;
    }

    @Override
    public String getSubTitle() {
        return "Test";
    }

    @Override
    public String getInnerLayoutName() {
        return "";
    }

    @Override
    public void setCardInnerContentView(GalleryItem item, View innerView) {

    }
}