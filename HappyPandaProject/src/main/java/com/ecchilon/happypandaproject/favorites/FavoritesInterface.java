package com.ecchilon.happypandaproject.favorites;

import android.os.AsyncTask;
import android.view.View;

import com.ecchilon.happypandaproject.AlbumItem;
import com.ecchilon.happypandaproject.sites.AlbumOverviewModuleInterface;

import java.util.List;

/**
 * Created by Alex on 1/25/14.
 */
public class FavoritesInterface implements AlbumOverviewModuleInterface {

    public FavoritesInterface(){

    }

    @Override
    public void getPage(int index, final AlbumPageCreatedCallback listener) {
        if(index > 0)
            listener.PageCreationFailed();

        new AsyncTask<Void,Void, List<AlbumItem>>(){
            @Override
            protected List<AlbumItem> doInBackground(Void... objects) {
                return FavoritesDatabaseHelper.getInstance().getAllFavorites();
            }

            @Override
            protected void onPostExecute(List<AlbumItem> items) {
                super.onPostExecute(items);
                listener.AlbumOverviewPageCreated(items);
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
    public void setCardInnerContentView(AlbumItem item, View innerView) {

    }
}
