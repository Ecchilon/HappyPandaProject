package com.ecchilon.happypandaproject;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.ecchilon.happypandaproject.gson.GsonDrawerItem;
import com.ecchilon.happypandaproject.gson.GsonNavItem;
import com.ecchilon.happypandaproject.navigation.NavDrawerFactory;
import com.ecchilon.happypandaproject.navigation.NavDrawerItem;
import com.ecchilon.happypandaproject.navigation.NavigationDrawerFragment;
import com.ecchilon.happypandaproject.navigation.navitems.INavItem;

public class GalleryActivity extends ActionBarActivity {

	public final static String FRAG_TAG = "GALLERY";

	private INavItem mNavItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mNavItem = GsonNavItem.getItem(getIntent().getStringExtra(GalleryFragment.NAV_KEY));

		String query = getIntent().getStringExtra(GalleryFragment.SEARCH_KEY);

		Bundle args = new Bundle();
		args.putString(GalleryFragment.NAV_KEY, GsonNavItem.getJson(mNavItem));
		if (query != null) {
			args.putString(GalleryFragment.SEARCH_KEY, query);
		}

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
		menu.clear();
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
		setStateBookmark(item, !isBookmarked());

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
				setStateBookmark(item, false);
				break;
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setStateBookmark(MenuItem bookmarkItem, boolean state) {
		if (state) {
			bookmarkItem.setEnabled(true);
			bookmarkItem.getIcon().setAlpha(255);
		}
		else {
			bookmarkItem.setEnabled(false);
			bookmarkItem.getIcon().setAlpha(127);
		}
	}

	/**
	 * Adds the current gallery to the bookmarks if it's not there already
	 */
	private void bookmark() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		List<NavDrawerItem> bookmarks = loadBookmarks();

		bookmarks.add(NavDrawerFactory.createBookmark(mNavItem));

		SharedPreferences.Editor editor = preferences.edit();

		editor.putString(NavigationDrawerFragment.BOOKMARKS, GsonDrawerItem.getJson(bookmarks));
		editor.putBoolean(NavigationDrawerFragment.BOOKMARK_CHANGED, true);
		editor.apply();
	}

	/**
	 * Checks if the current page is not in the bookmarks already
	 *
	 * @return
	 */
	private boolean isBookmarked() {
		List<NavDrawerItem> bookmarks = loadBookmarks();

		for (NavDrawerItem item : bookmarks) {
			if (item.getNavItem().equals(mNavItem)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Loads the bookmarks from the SharedPreferences
	 *
	 * @return a list of bookmarks, or an empty one if no bookmarks have been added yet
	 */
	private List<NavDrawerItem> loadBookmarks() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		if (preferences.contains(NavigationDrawerFragment.BOOKMARKS)) {
			return GsonDrawerItem.getItems(preferences.getString(NavigationDrawerFragment.BOOKMARKS, null));
		}
		else {
			return new ArrayList<NavDrawerItem>();
		}
	}
}
