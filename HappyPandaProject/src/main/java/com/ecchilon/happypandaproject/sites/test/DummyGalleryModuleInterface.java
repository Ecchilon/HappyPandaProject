package com.ecchilon.happypandaproject.sites.test;

import java.util.ArrayList;
import java.util.List;

import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;

/**
 * A simply dummy class to test the factory and the fragments
 * Created by Alex on 1/4/14.
 */
public class DummyGalleryModuleInterface implements GalleryOverviewModuleInterface {

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
}
