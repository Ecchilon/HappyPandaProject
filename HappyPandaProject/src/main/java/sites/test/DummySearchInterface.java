package sites.test;

import com.ecchilon.happypandaproject.GalleryItem;

import java.util.ArrayList;
import java.util.List;

import sites.SearchAbstract;

/**
 * Created by Alex on 1/17/14.
 */
public class DummySearchInterface extends SearchAbstract {

    public DummySearchInterface(String query) {
        super(query);
    }

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

    @Override
    public String getUrl(int index) {
        return null;
    }

    @Override
    public String getSubTitle() {
        return "Test";
    }
}
