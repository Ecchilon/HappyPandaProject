package com.ecchilon.happypandaproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ecchilon.happypandaproject.imageviewer.ImageViewerActivity;
import com.ecchilon.happypandaproject.sites.AlbumOverviewModuleInterface;
import com.ecchilon.happypandaproject.sites.util.SiteFactory;

/**
 * Created by Alex on 1/4/14.
 */
public class AlbumOverviewFragment extends Fragment implements AlbumOverviewAdapter.PageCreationFailedListener, AlbumOverviewAdapter.AlbumItemClickListener {
    public AlbumOverviewFragment() {}

    public ListView mList;
    public AlbumOverviewAdapter mAdapter;
    View mEndView;

    private int mSiteIndex = -1;

    public static final String SEARCH_KEY = "SEARCH";
    public static final String SITE_KEY = "SITE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        AlbumOverviewModuleInterface listInterface = null;

        //Index indicates the site module as set in the SiteFactory
        if(getArguments().containsKey(SITE_KEY)) {
            mSiteIndex = getArguments().getInt(SITE_KEY);
        }
        else
        {
            throw new IllegalArgumentException("No appropriate argument was provided!");
        }

        if(getArguments().containsKey(SEARCH_KEY)) {
            String query = getArguments().getString(SEARCH_KEY);
            listInterface = SiteFactory.getSearchInterface(mSiteIndex, query);
        }
        else {
            listInterface = SiteFactory.getOverviewInterface(mSiteIndex);
        }

        if(listInterface == null)
            throw new IllegalArgumentException("Site module wasn't loaded properly!");

        if(savedInstanceState == null)
        {
            ((TitleActivity)getActivity()).setTitle(listInterface.getTitle());
            ((TitleActivity)getActivity()).setSubTitle(listInterface.getSubTitle());
        }

        //restore adapter if it was saved
        final Object data = getActivity().getLastCustomNonConfigurationInstance();
        if(data instanceof AlbumOverviewAdapter)
            mAdapter = (AlbumOverviewAdapter)data;

        if(mAdapter == null)
        {
            mAdapter = new AlbumOverviewAdapter(listInterface, this, getActivity());
            mAdapter.setPageCreationFailedListener(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.loading_listview, container, false);
        mList = (ListView)rootView.findViewById(R.id.listView);
        mList.setEmptyView(rootView.findViewById(R.id.loading_view));

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mList.setAdapter(mAdapter);
        mList.setOnScrollListener(mAdapter);
    }

    /**
     *
     */
    @Override
    public void PageCreationFailed() {
        if(mEndView != null){
            mEndView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.fragment_overview_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_refresh:
                refresh();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * reset the adapter's content, and remove any footer views from the ListView
     */
    public void refresh()
    {
        if(mEndView != null)
            mEndView.setVisibility(View.GONE);

        if(mAdapter != null) {
            mAdapter.clear(true);
        }

        if(mList != null) {
            mList.invalidate();
        }
    }

    @Override
    public void AlbumItemClicked(AlbumItem item) {
        Intent imageViewIntent = new Intent(getActivity(), ImageViewerActivity.class);
        imageViewIntent.putExtra(ImageViewerActivity.GALLERY_ITEM_KEY, item);
        startActivity(imageViewIntent);
    }

    @Override
    public void AlbumItemDownloadClicked(AlbumItem item) {
        //TODO load service (Omg, we need a service oO) for downloading
    }

    @Override
    public void AlbumItemFavoriteClicked(AlbumItem item) {
        //TODO store in favorites.
    }
}