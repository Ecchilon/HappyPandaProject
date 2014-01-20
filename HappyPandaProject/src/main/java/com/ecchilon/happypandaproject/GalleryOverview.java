package com.ecchilon.happypandaproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.LruCache;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class GalleryOverview extends ActionBarActivity
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
    private static ImageLoader mImageLoader;
    private static int mNavigationPositionIndex = -1;

    public static ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public static void addRequest(Request request)
    {
        //sets tag to keep track of request while it's being handled, so it can be destroyed if
        //the navigation switches.
        request.setTag(mNavigationPositionIndex);
        mRequestQueue.add(request);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        getSupportActionBar().setIcon(R.drawable.actionbar_icon);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        mSubTitle = getSupportActionBar().getSubtitle();

        //Set up the singleton.
        mRequestQueue = Volley.newRequestQueue(this);
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        //stop all current requests from this part of the app. we don't need them anymore!
        if(mRequestQueue != null)
            mRequestQueue.cancelAll(mNavigationPositionIndex);
        mNavigationPositionIndex = position;

        Bundle args = new Bundle();
        args.putInt(GalleryOverviewFragment.SITE_KEY, position);
        GalleryOverviewFragment frag = new GalleryOverviewFragment();
        frag.setArguments(args);

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
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
        ActionBar actionBar = getSupportActionBar();
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

            SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
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
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    public void setSubTitle(String subTitle) {
        mSubTitle = subTitle;
        getSupportActionBar().setSubtitle(mSubTitle);
    }
}
