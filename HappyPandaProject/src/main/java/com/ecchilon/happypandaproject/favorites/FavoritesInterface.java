package com.ecchilon.happypandaproject.favorites;

import android.os.AsyncTask;
import android.view.View;

import com.ecchilon.happypandaproject.imageviewer.ImageViewerItem;
import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;

import java.util.List;

/**
 * Created by Alex on 1/25/14.
 */
public class FavoritesInterface implements GalleryOverviewModuleInterface {

    public FavoritesInterface(){

    }

    @Override
    public void getPage(int index, final GalleryPageCreatedCallback listener) {
        if(index > 0)
            listener.PageCreationFailed();

        new AsyncTask<Void,Void, List<ImageViewerItem>>(){
            @Override
            protected List<ImageViewerItem> doInBackground(Void... objects) {
                return FavoritesDatabaseHelper.getInstance().getAllFavorites();
            }

            @Override
            protected void onPostExecute(List<ImageViewerItem> items) {
                super.onPostExecute(items);
                listener.GalleryOverviewPageCreated(items);
            }
        }.execute();
    }

    @Override
    public String getSubTitle() {
        return "All";
    }
}
