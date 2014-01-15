package sites.test;

import com.ecchilon.happypandaproject.GalleryItem;

import java.util.ArrayList;
import java.util.List;

import sites.GalleryOverviewInterface;

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
                items.add(new GalleryItem());

            listener.GalleryOverviewPageCreated(items);
        }
        else
            listener.PageCreationFailed();
    }
}
