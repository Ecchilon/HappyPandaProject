package com.ecchilon.happypandaproject.imageviewer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;
import com.ecchilon.happypandaproject.sites.ImageViewerModuleInterface;
import com.ecchilon.happypandaproject.util.VolleySingleton;

/**
 * Created by Alex on 1/22/14.
 */
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    ImageViewerModuleInterface mModuleInterface;

    public ScreenSlidePagerAdapter(ImageViewerModuleInterface imageInterface, FragmentManager fm) {
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

    public class ScreenSlidePageFragment extends Fragment implements ImageViewerModuleInterface.GalleryImageURLCreatedCallback {

        NetworkImageView mNetworkImageView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            //TODO add PhotoviewAttacher to View
            mNetworkImageView = new NetworkImageView(getActivity());

            return mNetworkImageView;
        }

        @Override
        public void ImageURLCreated(String imageURL) {
            mNetworkImageView.setImageUrl(imageURL, VolleySingleton.getImageLoader());
        }

        @Override
        public void ImageURLCreationFailed() {
            //TODO set imagefailure! Or reload. w/e
        }
    }

}
