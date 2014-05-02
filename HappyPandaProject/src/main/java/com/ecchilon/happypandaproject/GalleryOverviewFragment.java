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
import com.ecchilon.happypandaproject.navigation.navitems.INavItem;
import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;
import com.ecchilon.happypandaproject.sites.util.SiteFactory;

/**
 * Created by Alex on 1/4/14.
 */
public class GalleryOverviewFragment extends Fragment implements GalleryOverviewAdapter.PageCreationFailedListener, GalleryOverviewAdapter.GalleryItemClickListener {
    public GalleryOverviewFragment() {}

	private ListView mList;
	private GalleryOverviewAdapter mAdapter;
	private View mEndView;

    private INavItem mSiteIndex;

    public static final String SEARCH_KEY = "SEARCH";
    public static final String NAV_KEY = "SITE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        GalleryOverviewModuleInterface listInterface = null;

        //Index indicates the site module as set in the SiteFactory
        if(getArguments().containsKey(NAV_KEY)) {
            mSiteIndex = getArguments().getParcelable(NAV_KEY);
        }
        else {
            throw new IllegalArgumentException("No appropriate argument was provided!");
        }

        if(getArguments().containsKey(SEARCH_KEY)) {
            String query = getArguments().getString(SEARCH_KEY);
            listInterface = SiteFactory.getSearchInterface(mSiteIndex, query);
        }
        else {
            listInterface = SiteFactory.getOverviewInterface(mSiteIndex);
        }

	    //FIXME should be far more user friendly! Error message
        if(listInterface == null)
            throw new IllegalArgumentException("Site module wasn't loaded properly!");

        if(savedInstanceState == null)
        {
            ((TitleActivity)getActivity()).setTitle(listInterface.getTitle());
            ((TitleActivity)getActivity()).setSubTitle(listInterface.getSubTitle());
        }

        //restore adapter if it was saved
        final Object data = getActivity().getLastCustomNonConfigurationInstance();
        if(data instanceof GalleryOverviewAdapter) {
	        mAdapter = (GalleryOverviewAdapter) data;
        }

        if(mAdapter == null)
        {
            mAdapter = new GalleryOverviewAdapter(listInterface, this, getActivity());
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
        if(mEndView != null) {
	        mEndView.setVisibility(View.GONE);
        }

        if(mAdapter != null) {
            mAdapter.clear(true);
        }

        if(mList != null) {
            mList.invalidate();
        }
    }

    @Override
    public void GalleryItemClicked(GalleryItem item) {
        Intent imageViewIntent = new Intent(getActivity(), ImageViewerActivity.class);
        imageViewIntent.putExtra(ImageViewerActivity.GALLERY_ITEM_KEY, item.toJSONString());
        startActivity(imageViewIntent);
    }

    @Override
    public void GalleryItemFavoriteClicked(GalleryItem item) {
        //TODO store GalleryItem in favorites.
    }
}
