package com.ecchilon.happypandaproject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class GalleryOverview extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, TitleActivity {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle, mSubTitle;

    private static RequestQueue mRequestQueue;

    /**
     * @return a Singleton of RequestQueue for all network requests
     */
    public static RequestQueue getRequestQueue() {
        //no null checking, since it needs the Context, so it's set at the start of the app.
        //should this ever be null, something went terribly wrong.
        return mRequestQueue;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        getActionBar().setIcon(R.drawable.actionbar_icon);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        mSubTitle = getActionBar().getSubtitle();

        //Set up the singleton.
        mRequestQueue = Volley.newRequestQueue(this);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Bundle args = new Bundle();
        args.putInt(GalleryOverviewFragment.SITE_KEY, position);
        GalleryOverviewFragment frag = new GalleryOverviewFragment();
        frag.setArguments(args);

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, frag)
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
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

            SearchView searchView = (SearchView) searchItem.getActionView();
            if (searchView != null) {
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        if(s != null && s.trim().length() > 0)
                        {
                            Intent searchIntent = new Intent(GalleryOverview.this, SearchActivity.class);
                            searchIntent.putExtra(GalleryOverviewFragment.SITE_KEY, mNavigationDrawerFragment.getCurrentSelectedPosition());
                            searchIntent.putExtra(GalleryOverviewFragment.SEARCH_KEY, s);

                            if(searchIntent.resolveActivity(getPackageManager()) != null)
                            {
                                GalleryOverview.this.startActivity(searchIntent);
                                searchItem.collapseActionView();
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
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(String title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    public void setSubTitle(String subTitle) {
        mSubTitle = subTitle;
        getActionBar().setSubtitle(mSubTitle);
    }
}
