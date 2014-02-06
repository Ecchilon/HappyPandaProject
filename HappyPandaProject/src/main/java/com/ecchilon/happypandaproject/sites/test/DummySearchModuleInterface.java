package com.ecchilon.happypandaproject.sites.test;

import android.view.View;

import com.ecchilon.happypandaproject.AlbumItem;
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
    public void setCardInnerContentView(AlbumItem item, View innerView) {

    }
}
