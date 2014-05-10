package com.ecchilon.happypandaproject.favorites;

import java.util.List;

import android.os.AsyncTask;
import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;

/**
 * Created by Alex on 1/25/14.
 */
public class FavoritesInterface implements GalleryOverviewModuleInterface<IMangaItem> {

	private FavoritesLoader mFavoritesLoader;

	public FavoritesInterface(FavoritesLoader favoritesLoader) {
		mFavoritesLoader = favoritesLoader;
	}

	@Override
	public void getPage(int index, final GalleryPageCreatedCallback<IMangaItem> listener) {
		if (index > 0) {
			listener.PageCreationFailed();
		}

		new AsyncTask<Void, Void, List<IMangaItem>>() {
			@Override
			protected List<IMangaItem> doInBackground(Void... objects) {
				return mFavoritesLoader.getFavorites();
			}

			@Override
			protected void onPostExecute(List<IMangaItem> items) {
				super.onPostExecute(items);
				listener.GalleryOverviewPageCreated(items);
			}
		}.execute();
	}
}
