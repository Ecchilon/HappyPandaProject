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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

	    Gson gson = new Gson();

	    mGalleryItem =  gson.fromJson(getIntent().getStringExtra(FRAG_TAG), INavItem.class);

	    Bundle args = new Bundle();
	    args.putString(GalleryFragment.NAV_KEY, gson.toJson(mGalleryItem));
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
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
		Gson gson = new Gson();

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		List<INavItem> bookmarks;
		if(preferences.contains(NavigationDrawerFragment.BOOKMARKS)) {
			bookmarks = gson.fromJson(preferences.getString(NavigationDrawerFragment.BOOKMARKS, null),
					new TypeToken<List<INavItem>>() {
					}.getType());
		}
		else {
			bookmarks = new ArrayList<INavItem>();
		}

		if(!bookmarks.contains(mGalleryItem)) {
			bookmarks.add(mGalleryItem);
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString(NavigationDrawerFragment.BOOKMARKS, gson.toJson(bookmarks, new TypeToken<List<INavItem>>() {
			}.getType()));
			editor.apply();
		}
	}

}
