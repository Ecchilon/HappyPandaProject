package com.ecchilon.happypandaproject.favorites;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import com.ecchilon.happypandaproject.gson.GsonMangaItem;
import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;

/**
 * Created by Alex on 1/25/14.
 */
public class FavoritesInterface implements GalleryOverviewModuleInterface {

	private static final String FAVORITES_KEY = "panda_favorites";

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

	/**
	 * Loads the favorites from preferences
	 *
	 * @return
	 */
	private List<IMangaItem> loadFavorites() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mAppContext);

		String jsonString = preferences.getString(FAVORITES_KEY, null);

		List<IMangaItem> items = GsonMangaItem.getItems(jsonString);

		return items;
	}
}
