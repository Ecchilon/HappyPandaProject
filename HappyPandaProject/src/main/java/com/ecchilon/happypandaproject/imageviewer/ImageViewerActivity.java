package com.ecchilon.happypandaproject.imageviewer;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.ecchilon.happypandaproject.GalleryItem;
import com.ecchilon.happypandaproject.GalleryOverviewFragment;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.sites.util.SiteFactory;

public class ImageViewerActivity extends ActionBarActivity {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private GestureViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    public static final String GALLERY_ITEM_KEY = "PANDA_GALLERY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ToggleInterface(false);

        int siteIndex = getIntent().getIntExtra(GalleryOverviewFragment.SITE_KEY, -1);
        GalleryItem galleryItem = getIntent().getParcelableExtra(GALLERY_ITEM_KEY);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (GestureViewPager)findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), SiteFactory.getGalleryPagesInterface(galleryItem));
        mPager.setAdapter(mPagerAdapter);
        mPager.setGestureDetector(new GestureDetector(this, new SingleTapListener()));
    }

	private Handler uiHandler = new Handler();
	private Runnable hideTask = new Runnable() {
        @SuppressLint("NewApi")
        @Override
        public void run() {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        }
    };
    private boolean lowProfile = false;
    private void ToggleInterface(boolean hideUIDelayed){
        if(!lowProfile){
            getSupportActionBar().hide();
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                uiHandler.postDelayed(hideTask, hideUIDelayed? 1000 : 0);
            }
        }
        else {
            getSupportActionBar().show();
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                //Remove callback in case this function was called before it could fire
                uiHandler.removeCallbacks(hideTask);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }

        lowProfile = !lowProfile;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private class SingleTapListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            ToggleInterface(true);
            return super.onSingleTapConfirmed(e);
        }
    }
}
