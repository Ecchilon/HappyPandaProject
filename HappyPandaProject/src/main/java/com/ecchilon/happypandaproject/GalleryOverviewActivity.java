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
import com.ecchilon.happypandaproject.navigation.INavVisitor;
import com.ecchilon.happypandaproject.navigation.NavigationDrawerFragment;
import com.ecchilon.happypandaproject.navigation.navitems.BookmarkedNavItem;
import com.ecchilon.happypandaproject.navigation.navitems.INavItem;
import com.ecchilon.happypandaproject.navigation.navitems.OverviewNavItem;
import com.ecchilon.happypandaproject.util.BookmarkActivity;
import com.ecchilon.happypandaproject.util.VolleySingleton;

public class GalleryOverviewActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, TitleActivity {

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

	/**
	 * Instantiate directly since @GalleryOverviewActivity#onNavigationDrawerItemSelected is called before onCreate
	 */
	private ItemVisitor mVisitor = new ItemVisitor();

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
		mVisitor = new ItemVisitor();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(INavItem item, int position) {
        //stop all current requests from this part of the app. we don't need them anymore!
        if(mVolleySingleton != null) {
            VolleySingleton.cancelRequests();
            mVolleySingleton.setNavigationPositionIndex(position);
        }

	    item.visit(mVisitor);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
        actionBar.setSubtitle(mSubTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.gallery_overview, menu);
            //Set up the search button
            final MenuItem searchItem = menu.findItem(R.id.action_search);

            SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            if (searchView != null) {
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        if(s != null && s.trim().length() > 0)
                        {
                            Intent searchIntent = new Intent(GalleryOverviewActivity.this, SearchActivity.class);
                            searchIntent.putExtra(GalleryOverviewFragment.NAV_KEY, mNavigationDrawerFragment.getCurrentSelectedItem());
                            searchIntent.putExtra(GalleryOverviewFragment.SEARCH_KEY, s);

                            if(searchIntent.resolveActivity(getPackageManager()) != null)
                            {
                                GalleryOverviewActivity.this.startActivity(searchIntent);
                                MenuItemCompat.collapseActionView(searchItem);
                            }
                        }

                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        return true;
                    }
                });
            }

            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

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

    @Override
    public void setTitle(String title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    public void setSubTitle(String subTitle) {
        mSubTitle = subTitle;
        getSupportActionBar().setSubtitle(mSubTitle);
    }

	/**
	 * Helper visitor for the INavItems
	 */
	private class ItemVisitor implements INavVisitor<Void> {
		/**
		 * Launches a separate activity for the bookmarked item.
		 * @param simpleNavItem The bookmarked item that was selected
		 * @return
		 */
		@Override
		public Void execute(BookmarkedNavItem simpleNavItem) {
			Intent bookmarkIntent = new Intent(GalleryOverviewActivity.this, BookmarkActivity.class);
			bookmarkIntent.putExtra(GalleryOverviewFragment.NAV_KEY, simpleNavItem);
			startActivity(bookmarkIntent);
			return null;
		}

		/**
		 * Replaces the current framgent of this activity with the new one based on the OverviewNavItem
		 * @param overviewNavItem
		 * @return
		 */
		@Override
		public Void execute(OverviewNavItem overviewNavItem) {
			Bundle args = new Bundle();
			args.putParcelable(GalleryOverviewFragment.NAV_KEY, overviewNavItem);
			GalleryOverviewFragment frag = new GalleryOverviewFragment();
			frag.setArguments(args);

			// update the main content by replacing fragments
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.container, frag, FRAG_TAG)
					.commit();
			return null;
		}
	}
}
