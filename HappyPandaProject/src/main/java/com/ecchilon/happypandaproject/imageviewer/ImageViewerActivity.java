package com.ecchilon.happypandaproject.imageviewer;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.ecchilon.happypandaproject.GalleryOverviewFragment;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.sites.util.SiteFactory;

public class ImageViewerActivity extends ActionBarActivity {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    public static final String GALLERY_URL_KEY= "PANDA_GALLERY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        int siteIndex = getIntent().getIntExtra(GalleryOverviewFragment.SITE_KEY, -1);
        String galleryURL = getIntent().getStringExtra(GALLERY_URL_KEY);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), SiteFactory.getImageViewerInterface(siteIndex, galleryURL));
        mPager.setAdapter(mPagerAdapter);
    }
}
