package com.ecchilon.happypandaproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import com.ecchilon.happypandaproject.favorites.FavoritesDatabaseHelper;
import com.ecchilon.happypandaproject.gson.GsonNavItem;
import com.ecchilon.happypandaproject.navigation.NavigationDrawerFragment;
import com.ecchilon.happypandaproject.navigation.navitems.INavPage;
import com.ecchilon.happypandaproject.util.VolleySingleton;

public class GalleryOverviewActivity extends ActionBarActivity
		implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	public final static String FRAG_TAG = "GALLERY";

	/**
	 * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle, mSubTitle;

	private VolleySingleton mVolleySingleton;
	private FavoritesDatabaseHelper databaseHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_overview);

		databaseHelper = new FavoritesDatabaseHelper(this);

		mNavigationDrawerFragment = (NavigationDrawerFragment)
				getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();
		mSubTitle = getSupportActionBar().getSubtitle();

		mVolleySingleton = new VolleySingleton(this);

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(
				R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(INavPage item, int position, boolean isFragment) {
		//stop all current requests from this part of the app. we don't need them anymore!
		if (mVolleySingleton != null) {
			VolleySingleton.cancelRequests();
			mVolleySingleton.setNavigationPositionIndex(position);
		}

		if (isFragment) {
			showFragment(item);
		}
		else {
			startNavActivity(item);
		}
	}

	/**
	 * Displays a new @GalleryFragment in this activity
	 *
	 * @param item
	 */
	private void showFragment(INavPage item) {
		Bundle args = new Bundle();
		args.putString(GalleryFragment.NAV_KEY, GsonNavItem.getJson(item));
		GalleryFragment frag = new GalleryFragment();
		frag.setArguments(args);

		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.container, frag, FRAG_TAG)
				.commit();
	}

	/**
	 * Starts a new @GalleryOverviewActivity activity with the given @INavPage
	 *
	 * @param item
	 */
	private void startNavActivity(INavPage item) {
		Intent bookmarkIntent = new Intent(GalleryOverviewActivity.this, GalleryActivity.class);
		bookmarkIntent.putExtra(GalleryFragment.NAV_KEY, GsonNavItem.getJson(item));
		startActivity(bookmarkIntent);
	}

	/**
	 * Resets the action bar to its default state
	 */
	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
		actionBar.setSubtitle(mSubTitle);
	}

	/**
	 * Creates a new options menu from @R.menu_gallery_overview with a search action view
	 *
	 * @param menu
	 * @return
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.menu_gallery_overview, menu);
			//Set up the search button
			final MenuItem searchItem = menu.findItem(R.id.action_search);

			SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
			if (searchView != null) {
				searchView.setOnQueryTextListener(new GalleryQueryListener(searchItem));
			}

			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Listens for presses of the @R.action_settings menu item
	 *
	 * @param item
	 * @return
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
			case R.id.action_settings:
				//TODO show setting activity
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Helper class for requesting a search query
	 */
	private class GalleryQueryListener implements SearchView.OnQueryTextListener {
		private MenuItem mSearchItem;

		public GalleryQueryListener(MenuItem searchItem) {
			mSearchItem = searchItem;
		}

		@Override
		public boolean onQueryTextSubmit(String s) {
			if (s != null && s.trim().length() > 0) {
				Intent searchIntent = new Intent(GalleryOverviewActivity.this, SearchActivity.class);
				searchIntent.putExtra(GalleryFragment.NAV_KEY, GsonNavItem.getJson(
						mNavigationDrawerFragment.getCurrentSelectedItem()));
				searchIntent.putExtra(GalleryFragment.SEARCH_KEY, s);

				if (searchIntent.resolveActivity(getPackageManager()) != null) {
					GalleryOverviewActivity.this.startActivity(searchIntent);
					MenuItemCompat.collapseActionView(mSearchItem);
				}
			}

			return true;
		}

		@Override
		public boolean onQueryTextChange(String s) {
			return true;
		}
	}
}
