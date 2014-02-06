package com.ecchilon.happypandaproject.sites.test;

import android.view.View;

import com.ecchilon.happypandaproject.AlbumItem;
import com.ecchilon.happypandaproject.sites.AlbumOverviewModuleInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * A simply dummy class to test the factory and the fragments
 * Created by Alex on 1/4/14.
 */
public class DummyAlbumModuleInterface implements AlbumOverviewModuleInterface {

    @Override
    public void getPage(int index, AlbumPageCreatedCallback listener) {
        if(index < 3) {
            List<AlbumItem> items = new ArrayList<AlbumItem>();
            for(int i = 0; i < 10; i++)
                items.add(new AlbumItem("TitleTest " + (index*10 + i), null, null, false, false));

            listener.AlbumOverviewPageCreated(items);
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
    public void setCardInnerContentView(AlbumItem item, View innerView) {

    }
}
