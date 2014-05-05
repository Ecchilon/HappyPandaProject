package com.ecchilon.happypandaproject.favorites;

import java.util.List;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.gson.GsonDrawerItem;
import com.ecchilon.happypandaproject.navigation.NavDrawerFactory;
import com.ecchilon.happypandaproject.navigation.NavDrawerItem;
import com.ecchilon.happypandaproject.navigation.NavigationDrawerFragment;
import com.mobeta.android.dslv.DragSortController;
import com.mobeta.android.dslv.DragSortListView;

public class BookmarkActivity extends ActionBarActivity implements DragSortListView.DropListener, DragSortListView
		.RemoveListener, AdapterView.OnItemClickListener, UndoBarController.UndoListener {

	private DragSortListView mListView;
	private DragSortBookmarkAdapter mAdapter;
	private List<NavDrawerItem> mBookmarks;
	private UndoBarController mUndoControl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bookmark);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mBookmarks = NavDrawerFactory.loadBookmarks(this);
		mAdapter = new DragSortBookmarkAdapter(mBookmarks);

		mListView = (DragSortListView) findViewById(R.id.bookmark_list);

		DragSortController controller = new DragSortController(mListView);
		controller.setDragHandleId(R.id.drag_handle);
		controller.setRemoveEnabled(true);
		controller.setDragInitMode(1);
		controller.setRemoveMode(DragSortController.FLING_REMOVE);

		mListView.setDropListener(this);
		mListView.setRemoveListener(this);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		mListView.setFloatViewManager(controller);
		mListView.setOnTouchListener(controller);
		mListView.setDragEnabled(true);

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			mUndoControl = new UndoBarController(findViewById(R.id.undobar), this);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bookmark, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			NavUtils.navigateUpFromSameTask(this);
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void drop(int from, int to) {
		NavDrawerItem movedItem = mBookmarks.remove(from);
		mBookmarks.add(to, movedItem);

		updateBookmarks();
	}

	@Override
	public void remove(int i) {
		NavDrawerItem item = mBookmarks.remove(i);

		if(mUndoControl != null) {
			mUndoControl.showUndoBar(false, getString(R.string.undo_message), new NavDrawerParcelable(item, i));
		}

		updateBookmarks();
	}

	private void updateBookmarks() {
		mAdapter.notifyDataSetChanged();

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();

		editor.putString(NavigationDrawerFragment.BOOKMARKS, GsonDrawerItem.getJson(mBookmarks));
		editor.putBoolean(NavigationDrawerFragment.BOOKMARK_CHANGED, true);
		editor.apply();
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		//TODO show rename dialog
	}

	@Override
	public void onUndo(Parcelable token) {
		NavDrawerParcelable parcelable = (NavDrawerParcelable)token;
		mBookmarks.add(parcelable.getPosition(), parcelable.getItem());
		updateBookmarks();
	}

	/**
	 * Helper class to store the item as a parcelable
	 */
	private static class NavDrawerParcelable implements Parcelable {

		private NavDrawerItem mItem;
		private int mPosition;

		public NavDrawerItem getItem() {
			return mItem;
		}

		public int getPosition() {
			return mPosition;
		}

		public NavDrawerParcelable(NavDrawerItem item, int position) {
			mItem = item;
			mPosition = position;
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel parcel, int i) {
			parcel.writeString(GsonDrawerItem.getJson(mItem));
			parcel.writeInt(mPosition);
		}

		public static final Parcelable.Creator<NavDrawerParcelable> CREATOR
				= new Parcelable.Creator<NavDrawerParcelable>() {
			public NavDrawerParcelable createFromParcel(Parcel in) {
				return new NavDrawerParcelable(in);
			}

			public NavDrawerParcelable[] newArray(int size) {
				return new NavDrawerParcelable[size];
			}
		};

		private NavDrawerParcelable(Parcel in) {
		    mItem = GsonDrawerItem.getItem(in.readString());
			mPosition = in.readInt();
		}
	}
}
