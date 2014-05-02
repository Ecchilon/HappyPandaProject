package com.ecchilon.happypandaproject.imageviewer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ecchilon.happypandaproject.sites.GalleryPagesModuleInterface;

/**
 * Created by Alex on 1/22/14.
 */
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private GalleryPagesModuleInterface mModuleInterface;

    public ScreenSlidePagerAdapter(FragmentManager fm, GalleryPagesModuleInterface imageInterface) {
        super(fm);

        mModuleInterface = imageInterface;
    }

    @Override
    public Fragment getItem(int position) {
        ScreenSlidePageFragment frag = new ScreenSlidePageFragment();
        mModuleInterface.getImage(position, frag);
        return frag;
    }

    @Override
    public int getCount() {
        return mModuleInterface.getPageCount();
    }
}
