package com.ecchilon.happypandaproject;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.ecchilon.happypandaproject.navigation.NavigationDrawerFragment;
import com.ecchilon.happypandaproject.gson.GsonNavItem;
import com.ecchilon.happypandaproject.navigation.navitems.INavItem;

public class GalleryActivity extends ActionBarActivity {

	public final static String FRAG_TAG = "GALLERY";

	INavItem mGalleryItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);

		mGalleryItem = GsonNavItem.getItem(getIntent().getStringExtra(GalleryFragment.NAV_KEY));

		Bundle args = new Bundle();
		args.putString(GalleryFragment.NAV_KEY, GsonNavItem.getJson(mGalleryItem));
		GalleryFragment frag = new GalleryFragment();
		frag.setArguments(args);

		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.container, frag, FRAG_TAG)
				.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_gallery, menu);

		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Disables the bookmark if it's already in the bookmarks
	 *
	 * @param menu
	 * @return
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem item = menu.findItem(R.id.action_bookmark);
		if (isBookmarked()) {
			item.setEnabled(false);
			//TODO set greyed icon
		}

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
			case R.id.action_settings:
				break;
			case R.id.action_bookmark:
				bookmark();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Adds the current gallery to the bookmarks if it's not there already
	 */
	private void bookmark() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		List<INavItem> bookmarks = loadBookmarks();

		bookmarks.add(mGalleryItem);

		SharedPreferences.Editor editor = preferences.edit();

		editor.putString(NavigationDrawerFragment.BOOKMARKS, GsonNavItem.getJson(bookmarks));
		editor.apply();
	}

	/**
	 * Checks if the current page is not in the bookmarks already
	 *
	 * @return
	 */
	private boolean isBookmarked() {
		List<INavItem> bookmarks = loadBookmarks();
		return bookmarks.contains(mGalleryItem);
	}

	/**
	 * Loads the bookmarks from the SharedPreferences
	 *
	 * @return a list of bookmarks, or an empty one if no bookmarks have been added yet
	 */
	private List<INavItem> loadBookmarks() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		if (preferences.contains(NavigationDrawerFragment.BOOKMARKS)) {
			return GsonNavItem.getItems(preferences.getString(NavigationDrawerFragment.BOOKMARKS, null));
		}
		else {
			return new ArrayList<INavItem>();
		}
	}
}
