package com.ecchilon.happypandaproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ecchilon.happypandaproject.imageviewer.ImageViewerItem;
import com.ecchilon.happypandaproject.imageviewer.ImageViewerActivity;
import com.ecchilon.happypandaproject.gson.GsonNavItem;
import com.ecchilon.happypandaproject.navigation.navitems.INavItem;
import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;
import com.ecchilon.happypandaproject.sites.test.DummyAdapter;
import com.ecchilon.happypandaproject.sites.util.SiteFactory;

/**
 * Created by Alex on 1/4/14.
 */
public class GalleryFragment extends Fragment implements GalleryAdapter.PageCreationFailedListener,
		GalleryAdapter.GalleryItemClickListener {
	public GalleryFragment() {
	}

	private ListView mList;
	private GalleryAdapter mAdapter;

	private INavItem mGalleryItem;

	/**
	 * Key in arguments from which the search query string is retrieved
	 */
	public static final String SEARCH_KEY = "SEARCH";

	/**
	 * Key in arguments from which the navigation item is retrieved
	 */
	public static final String NAV_KEY = "SITE";

	/**
	 * Constructs a new gallery of items based on the INavItem that is put in as an argument
	 *
	 * @param savedInstanceState
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		if (!getArguments().containsKey(NAV_KEY)) {
			throw new IllegalArgumentException(
					"This fragment should always be provided with an appropriate NAV_KEY argument!");
		}

		String galleryString = getArguments().getString(NAV_KEY);

		mGalleryItem = GsonNavItem.getItem(galleryString);

		GalleryOverviewModuleInterface listInterface;

		if (getArguments().containsKey(SEARCH_KEY)) {
			String query = getArguments().getString(SEARCH_KEY);
			listInterface = SiteFactory.getSearchInterface(mGalleryItem, query);
		}
		else {
			listInterface = SiteFactory.getOverviewInterface(mGalleryItem);
		}

		//FIXME should be far more user friendly! Error message
		if (listInterface == null) {
			throw new IllegalArgumentException("Site module wasn't loaded properly!");
		}

		if (savedInstanceState == null) {
			if (!(getActivity() instanceof ActionBarActivity)) {
				throw new IllegalArgumentException("Activity using this fragment must extends ActionBarActivity!");
			}

			ActionBarActivity activity = (ActionBarActivity) getActivity();
			activity.getSupportActionBar().setTitle(mGalleryItem.getTitle());
			activity.getSupportActionBar().setSubtitle(listInterface.getSubTitle());
		}

		//restore adapter if it was saved
		final Object data = getActivity().getLastCustomNonConfigurationInstance();
		if (data instanceof GalleryAdapter) {
			mAdapter = (GalleryAdapter) data;
		}

		if (mAdapter == null) {
			mAdapter = SiteFactory.getGalleryAdapter(mGalleryItem, listInterface, this);
			mAdapter.setPageCreationFailedListener(this);
		}
	}

	/**
	 * Constructs the new ListView with an empty (loading) view as well
	 *
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.loading_listview, container, false);
		mList = (ListView) rootView.findViewById(R.id.listView);
		mList.setEmptyView(rootView.findViewById(R.id.loading_view));

		return rootView;
	}

	/**
	 * Attaches the adapter to the list view
	 *
	 * @param view
	 * @param savedInstanceState
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mList.setAdapter(mAdapter);
		mList.setOnScrollListener(mAdapter);
	}

	/**
	 * Displays a little footer notifying the user of unavailability of pages
	 */
	@Override
	public void PageCreationFailed() {
		//TODO Display a little footer notifying the user of unavailability of pages
	}

	/**
	 * Constructs a new menu from @R.menu_gallery_fragment
	 *
	 * @param menu
	 * @param inflater
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);

		inflater.inflate(R.menu.menu_gallery_fragment, menu);
	}

	/**
	 * closes the the overflow view if it's open
	 */
	@Override
	public void onPause() {
		super.onPause();

		mAdapter.closeCurrentOverflowMenu();
	}

	/**
	 * Checks for refresh pressing
	 *
	 * @param item
	 * @return
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_refresh:
				refresh();
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * reset the adapter's content, and remove any footer views from the ListView
	 */
	public void refresh() {
		if (mAdapter != null) {
			mAdapter.clear(true);
		}

		if (mList != null) {
			mList.invalidate();
		}
	}

	/**
	 * Opens an @ImageViewerActivity activity with the provided @ImageViewerItem
	 *
	 * @param item
	 */
	@Override
	public void GalleryItemClicked(ImageViewerItem item) {
		Intent imageViewIntent = new Intent(getActivity(), ImageViewerActivity.class);
		imageViewIntent.putExtra(ImageViewerActivity.GALLERY_ITEM_KEY, item.toJSONString());
		startActivity(imageViewIntent);
	}

	/**
	 * Opens a new @GalleryActivity with the provided @INavItem
	 * @param item
	 */
	@Override
	public void GalleryNavItemClicked(INavItem item) {
		Intent galleryIntent = new Intent(getActivity(), GalleryActivity.class);
		galleryIntent.putExtra(NAV_KEY, GsonNavItem.getJson(item));
		startActivity(galleryIntent);
	}

	@Override
	public void GalleryItemFavoriteClicked(ImageViewerItem item) {
		//TODO store ImageViewerItem in favorites.
	}
}
