package com.ecchilon.happypandaproject.imageviewer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ecchilon.happypandaproject.sites.ImageViewerModuleInterface;

/**
 * Created by Alex on 1/22/14.
 */
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    ImageViewerModuleInterface mModuleInterface;

    public ScreenSlidePagerAdapter(FragmentManager fm, ImageViewerModuleInterface imageInterface) {
        super(fm);

        mModuleInterface = imageInterface;
    }

    @Override
    public Fragment getItem(int position) {
        ScreenSlidePageFragment frag = new ScreenSlidePageFragment();
        mModuleInterface.getImageURL(position, frag);
        return frag;
    }

    @Override
    public int getCount() {
        return mModuleInterface.getPageCount();
    }
}
