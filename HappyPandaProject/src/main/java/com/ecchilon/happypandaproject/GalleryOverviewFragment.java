package com.ecchilon.happypandaproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import sites.util.GalleryOverviewInterface;
import sites.util.SiteFactory;

/**
 * Created by Ecchilon on 1/4/14.
 */
public class GalleryOverviewFragment extends Fragment implements GalleryViewAdapter.PageCreationFailedListener {
    public GalleryOverviewFragment() {}

    public ListView mList;
    public GalleryViewAdapter mAdapter;

    public static final String SEARCH_KEY = "SEARCH";
    public static final String SITE_KEY = "SITE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gallery_fragment_overview, container, false);

        mList = (ListView)rootView.findViewById(R.id.list_overview);

        GalleryOverviewInterface listInterface = null;

        if(getArguments().containsKey(SITE_KEY)) {
            int index = Integer.parseInt(getArguments().getString(SITE_KEY));
            listInterface = SiteFactory.getOverviewInterface(index);
        }
        else if(getArguments().containsKey(SEARCH_KEY)) {
            int index = Integer.parseInt(getArguments().getString(SEARCH_KEY));
            listInterface = SiteFactory.getSearchInterface(index);
        }
        else {
            throw new IllegalArgumentException("No appropriate argument was provided!");
        }

        mAdapter = new GalleryViewAdapter(listInterface);
        mAdapter.setPageCreationFailedListener(this);

        mList.setAdapter(mAdapter);

        return rootView;
    }

    View mEndView;

    /**
     *
     */
    @Override
    public void PageCreationFailed() {
        ListView overview = (ListView)getView().findViewById(R.id.list_overview);
        if(mEndView == null)
            mEndView = View.inflate(getActivity(), R.layout.end_of_overview_item, (ViewGroup) getView());
        overview.addFooterView(mEndView);
    }

    /**
     * reset the adapter's content, and remove any footer views from the ListView
     */
    public void refresh()
    {
        if(mAdapter != null) {
            mAdapter.clear(true);
        }
        if(mList != null) {
            if(mEndView != null)
                mList.removeFooterView(mEndView);
        }
    }
}
