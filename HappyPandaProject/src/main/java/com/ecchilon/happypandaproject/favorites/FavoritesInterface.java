package com.ecchilon.happypandaproject.favorites;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;

/**
 * Created by Alex on 1/25/14.
 */
public class FavoritesInterface implements GalleryOverviewModuleInterface {

	private Context mAppContext;

	public FavoritesInterface(Context appContext) {
		mAppContext = appContext;
	}

	@Override
	public void getPage(int index, final GalleryPageCreatedCallback listener) {
		if (index > 0) {
			listener.PageCreationFailed();
		}

		new AsyncTask<Void, Void, List<IMangaItem>>() {
			@Override
			protected List<IMangaItem> doInBackground(Void... objects) {
				return loadFavorites();
			}

			@Override
			protected void onPostExecute(List<IMangaItem> items) {
				super.onPostExecute(items);
				listener.GalleryOverviewPageCreated(items);
			}
		}.execute();
	}

	//TODO implement!
	private List<IMangaItem> loadFavorites() {
		return new ArrayList<IMangaItem>();
	}
}
