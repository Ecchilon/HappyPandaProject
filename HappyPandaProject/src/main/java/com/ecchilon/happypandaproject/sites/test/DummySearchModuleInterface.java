package com.ecchilon.happypandaproject.sites.test;

import java.util.ArrayList;
import java.util.List;

import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.sites.SearchAbstract;

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
	        List<IMangaItem> items = new ArrayList<IMangaItem>();
	        for (int i = 0; i < 10; i++) {
		        items.add(new DummyMangaItem("TitleTest " + (index * 10 + i)));
	        }

	        listener.GalleryOverviewPageCreated(items);
        }
        else
            listener.PageCreationFailed();
    }

    @Override
    public String getUrl(int index) {
        return null;
    }
}
