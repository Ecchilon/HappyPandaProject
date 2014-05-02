package com.ecchilon.happypandaproject.util;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.ecchilon.happypandaproject.GalleryOverviewFragment;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.navigation.navitems.BookmarkedNavItem;

public class BookmarkActivity extends ActionBarActivity {

	public final static String FRAG_TAG = "GALLERY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

	    BookmarkedNavItem mBookmarkItem = getIntent().getParcelableExtra(FRAG_TAG);

	    Bundle args = new Bundle();
	    args.putParcelable(GalleryOverviewFragment.NAV_KEY, mBookmarkItem);
	    GalleryOverviewFragment frag = new GalleryOverviewFragment();
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
        getMenuInflater().inflate(R.menu.bookmark, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
	        //TODO show setting activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
