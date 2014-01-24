package com.ecchilon.happypandaproject.imageviewer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.sites.ImageViewerModuleInterface;
import com.ecchilon.happypandaproject.util.NetworkListenerImageView;
import com.ecchilon.happypandaproject.util.VolleySingleton;

/**
 * Created by Alex on 1/24/14.
 */
public class ScreenSlidePageFragment extends Fragment implements ImageViewerModuleInterface.GalleryImageURLCreatedCallback, NetworkListenerImageView.NetworkImageListener {

    public static final int MAX_NUM_RETRIES = 2;

    NetworkListenerImageView mNetworkImageView;
    ProgressBar loadingBar;
    TextView failureText;

    String mImageUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.loading_image_view, container, false);

        mNetworkImageView = (NetworkListenerImageView) view.findViewById(R.id.image_view);
        loadingBar = (ProgressBar) view.findViewById(R.id.loading_view);
        failureText = (TextView) view.findViewById(R.id.failure_text);

        mNetworkImageView.setNetworkImageListener(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(mImageUrl != null)
            mNetworkImageView.setImageUrl(mImageUrl, VolleySingleton.getImageLoader());
    }

    @Override
    public void ImageURLCreated(String imageURL) {
        if(mNetworkImageView != null && imageURL != null)
            mNetworkImageView.setImageUrl(imageURL, VolleySingleton.getImageLoader());

        mImageUrl = imageURL;
    }

    @Override
    public void ImageURLCreationFailed() {
        //TODO so what happens now?...
    }

    int retries = 0;

    @Override
    public void ImageLoadFailed(NetworkListenerImageView view) {
        /**if image is not the same (listener has been set to this image), just ignore since it's
         an view that was discarded? */
        if(mNetworkImageView != view) return;
        //retries url again if it failed
        if(mNetworkImageView != null && mImageUrl != null && retries++ < MAX_NUM_RETRIES)
            mNetworkImageView.setImageUrl(mImageUrl, VolleySingleton.getImageLoader());
        else
        {
            loadingBar.setVisibility(View.GONE);
            failureText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void ImageLoadSucceeded(NetworkListenerImageView view) {
        //TODO disable loading
        loadingBar.setVisibility(View.GONE);
    }
}