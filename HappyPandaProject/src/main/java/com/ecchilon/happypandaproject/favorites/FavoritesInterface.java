package com.ecchilon.happypandaproject.favorites;

import android.os.AsyncTask;
import android.view.View;

import com.ecchilon.happypandaproject.GalleryItem;
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

        new AsyncTask<Void,Void, List<GalleryItem>>(){
            @Override
            protected List<GalleryItem> doInBackground(Void... objects) {
                return FavoritesDatabaseHelper.getInstance().getAllFavorites();
            }

            @Override
            protected void onPostExecute(List<GalleryItem> items) {
                super.onPostExecute(items);
                listener.GalleryOverviewPageCreated(items);
            }
        }.execute();
    }

    @Override
    public String getTitle() {
        return "Favorites";
    }

    @Override
    public String getSubTitle() {
        return "All";
    }

    @Override
    public String getInnerLayoutName() {
        return null;
    }

    @Override
    public void setCardInnerContentView(GalleryItem item, View innerView) {

    }
}
