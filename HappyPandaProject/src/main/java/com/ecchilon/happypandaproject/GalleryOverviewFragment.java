package com.ecchilon.happypandaproject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import sites.GalleryOverviewInterface;
import sites.util.SiteFactory;

/**
 * Created by Alex on 1/4/14.
 */
public class GalleryOverviewFragment extends Fragment implements GalleryViewAdapter.PageCreationFailedListener {
    public GalleryOverviewFragment() {}

    public ListView mList;
    public GalleryViewAdapter mAdapter;

    public static final String SEARCH_KEY = "SEARCH";
    public static final String SITE_KEY = "SITE";
    View mEndView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        GalleryOverviewInterface listInterface = null;

        int index = -1;

        //use the factory to determine which site-interface gets loaded
        if(getArguments().containsKey(SITE_KEY)) {
            index = getArguments().getInt(SITE_KEY);
        }
        else
        {
            throw new IllegalArgumentException("No appropriate argument was provided!");
        }

        if(getArguments().containsKey(SEARCH_KEY)) {
            String query = getArguments().getString(SEARCH_KEY);
            listInterface = SiteFactory.getSearchInterface(index, query);
        }
        else {
            listInterface = SiteFactory.getOverviewInterface(index);
        }

        ((TitleActivity)getActivity()).setTitle(listInterface.getTitle());
        ((TitleActivity)getActivity()).setSubTitle(listInterface.getSubTitle());

        mAdapter = new GalleryViewAdapter(listInterface);
        mAdapter.setPageCreationFailedListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.loading_listview, container, false);
        mList = (ListView)rootView.findViewById(R.id.listView);
        mList.setEmptyView(rootView.findViewById(R.id.emptyView));

       //mEndView = View.inflate(getActivity(), R.layout.end_of_overview_item, null);

        //mEndView.setVisibility(View.GONE);
        //mList.addFooterView(mEndView);

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
}
