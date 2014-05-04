package com.ecchilon.happypandaproject.util;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.ecchilon.happypandaproject.GalleryFragment;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.navigation.NavigationDrawerFragment;
import com.ecchilon.happypandaproject.navigation.navitems.INavItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GalleryActivity extends ActionBarActivity {

	public final static String FRAG_TAG = "GALLERY";

	INavItem mGalleryItem;
	Gson mGson = new Gson();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);

		mGson = new Gson();

		mGalleryItem = mGson.fromJson(getIntent().getStringExtra(FRAG_TAG), INavItem.class);

		Bundle args = new Bundle();
		args.putString(GalleryFragment.NAV_KEY, mGson.toJson(mGalleryItem));
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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_gallery, menu);

		MenuItem item = menu.findItem(R.id.action_bookmark);
		if(isBookmarked()) {
			//TODO disable item icon
		}

		return true;
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
		editor.putString(NavigationDrawerFragment.BOOKMARKS,
				mGson.toJson(bookmarks, new TypeToken<List<INavItem>>() {
				}.getType())
		);
		editor.apply();
	}

	/**
	 * Checks if the current page is not in the bookmarks already
	 * @return
	 */
	private boolean isBookmarked() {
		List<INavItem> bookmarks = loadBookmarks();
		return bookmarks.contains(mGalleryItem);
	}

	private List<INavItem> loadBookmarks() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		if (preferences.contains(NavigationDrawerFragment.BOOKMARKS)) {
			return mGson.fromJson(preferences.getString(NavigationDrawerFragment.BOOKMARKS, null),
					new TypeToken<List<INavItem>>() {
					}.getType()
			);
		}
		else {
			return new ArrayList<INavItem>();
		}
	}
}
