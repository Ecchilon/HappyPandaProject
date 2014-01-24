package com.ecchilon.happypandaproject.imageviewer;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

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

    public static final String GALLERY_URL_KEY= "PANDA_GALLERY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //If system provides, use that. Otherwise, use a gesturedetector
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new VisibilityListener());
            ToggleInterface();
        }



        int siteIndex = getIntent().getIntExtra(GalleryOverviewFragment.SITE_KEY, -1);
        String galleryURL = getIntent().getStringExtra(GALLERY_URL_KEY);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (GestureViewPager)findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), SiteFactory.getImageViewerInterface(siteIndex, galleryURL));
        mPager.setAdapter(mPagerAdapter);
        mPager.setGestureDetector(new GestureDetector(this, new SingleTapListener()));
    }

    private boolean lowProfile = false;
    private void ToggleInterface(){
        int flag = -1;

        if(!lowProfile){
            getSupportActionBar().hide();
            flag = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }
        else {
            getSupportActionBar().show();
            flag = View.SYSTEM_UI_FLAG_VISIBLE;
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            getWindow().getDecorView().setSystemUiVisibility(flag);

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
            ToggleInterface();
            return super.onSingleTapConfirmed(e);
        }
    }

    /** wont be called unless at least honeycomb */
    @SuppressLint("NewApi")
    private class VisibilityListener implements View.OnSystemUiVisibilityChangeListener {
        @Override
        public void onSystemUiVisibilityChange(int i) {
            /*if(i == View.SYSTEM_UI_FLAG_VISIBLE)
                getSupportActionBar().show();
            else
                getSupportActionBar().hide();*/
        }
    }
}
