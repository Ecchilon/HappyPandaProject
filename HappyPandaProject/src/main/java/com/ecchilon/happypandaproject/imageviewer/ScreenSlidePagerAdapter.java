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

    public class ScreenSlidePageFragment extends Fragment implements ImageViewerModuleInterface.GalleryImageURLCreatedCallback {

        NetworkImageView mNetworkImageView;

        String mImageUrl;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            mNetworkImageView = new NetworkImageView(getActivity());

            return mNetworkImageView;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            //TODO add PhotoviewAttacher to View

            if(mImageUrl != null)
               mNetworkImageView.setImageUrl(mImageUrl, VolleySingleton.getImageLoader());
        }

        @Override
        public void ImageURLCreated(String imageURL) {
            if(mNetworkImageView != null)
                mNetworkImageView.setImageUrl(imageURL, VolleySingleton.getImageLoader());

            mImageUrl = imageURL;
        }

        @Override
        public void ImageURLCreationFailed() {
            //TODO set imagefailure! Or reload. w/e
        }
    }

}
