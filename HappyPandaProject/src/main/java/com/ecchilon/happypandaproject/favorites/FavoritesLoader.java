package com.ecchilon.happypandaproject.favorites;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.ecchilon.happypandaproject.gson.GsonMangaItem;
import com.ecchilon.happypandaproject.imageviewer.IMangaItem;

/**
 * Created by Alex on 10-5-2014.
 */
public class FavoritesLoader {

	private static final String FAVORITES_KEY = "panda_favorites";

	private static final String FAVORITES_CHANGED = "panda_favorites_changed";

	private SharedPreferences mPreferences;

	private List<IMangaItem> mFavorites;

	public FavoritesLoader(Context context) {
		mPreferences = PreferenceManager.getDefaultSharedPreferences(context);

		reloadFavorites();
	}

	private void reloadFavorites() {
		mFavorites = GsonMangaItem.getItems(mPreferences.getString(FAVORITES_KEY, null));

		if (mFavorites == null) {
			mFavorites = new ArrayList<IMangaItem>();
		}
	}

	public ArrayList<IMangaItem> getFavorites() {
		if (isDirty()) {
			reloadFavorites();
		}

		return new ArrayList<IMangaItem>(mFavorites);
	}

	public boolean containsFavorite(IMangaItem item) {
		if (isDirty()) {
			reloadFavorites();
		}

		return mFavorites.contains(item);
	}

	public void addFavorite(IMangaItem item) {
		if (isDirty()) {
			reloadFavorites();
		}

		mFavorites.add(item);
		writeFavorites();
	}

	public void addFavorite(IMangaItem item, int position) {
		if (isDirty()) {
			reloadFavorites();
		}

		mFavorites.add(position, item);
		writeFavorites();
	}

	public boolean removeFavorite(IMangaItem item) {
		if (isDirty()) {
			reloadFavorites();
		}

		if (mFavorites.remove(item)) {
			writeFavorites();
			return true;
		}
		else {
			return false;
		}
	}

	private void writeFavorites() {
		mPreferences.edit().putString(FAVORITES_KEY, GsonMangaItem.getJson(mFavorites)).apply();
	}

	private boolean isDirty() {
		return mPreferences.getBoolean(FAVORITES_CHANGED, true);
	}
}
