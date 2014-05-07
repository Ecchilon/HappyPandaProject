package com.ecchilon.happypandaproject.favorites;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
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

/**
 * Activity that allows the user to organize their bookmarks, if they have any. Created by Alex on 5/6/2014.
 */
public class BookmarkActivity extends ActionBarActivity implements DragSortListView.DropListener, DragSortListView
		.RemoveListener, AdapterView.OnItemClickListener, UndoBarController.UndoListener,
		RenameDialogFragment.RenameListener {

	private DragSortListView mListView;
	private DragSortBookmarkAdapter mAdapter;
	private List<NavDrawerItem> mBookmarks;
	private UndoBarController mUndoControl;

	/**
	 * Constructs the DragSortListView
	 *
	 * @param savedInstanceState
	 */
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

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			mUndoControl = new UndoBarController(findViewById(R.id.undobar), this);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.bookmark, menu);
		return true;
	}

	/**
	 * Handles sorting and up navigation
	 *
	 * @param item
	 * @return
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_sort_abc:
				sortBookmarks();
				break;
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Reposition bookmark in list and update preferences
	 *
	 * @param from
	 * @param to
	 */
	@Override
	public void drop(int from, int to) {
		NavDrawerItem movedItem = mBookmarks.remove(from);
		mBookmarks.add(to, movedItem);

		updateBookmarks();
	}

	/**
	 * Remove bookmark with option to restore it through UndoBar
	 *
	 * @param i
	 */
	@Override
	public void remove(int i) {
		NavDrawerItem item = mBookmarks.remove(i);

		if (mUndoControl != null) {
			mUndoControl.showUndoBar(false, getString(R.string.undo_message), new NavDrawerParcelable(item, i));
		}

		updateBookmarks();
	}

	/**
	 * Opens DialogFragment for renaming the clicked bookmark
	 *
	 * @param adapterView
	 * @param view
	 * @param i
	 * @param l
	 */
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		RenameDialogFragment fragment = new RenameDialogFragment();
		fragment.setRenameListener(this);
		fragment.setItem(mAdapter.getItem(i));
		fragment.show(getSupportFragmentManager(), "RENAME");
	}

	/**
	 * Restores previously removed bookmark
	 *
	 * @param token
	 */
	@Override
	public void onUndo(Parcelable token) {
		NavDrawerParcelable parcelable = (NavDrawerParcelable) token;
		mBookmarks.add(parcelable.getPosition(), parcelable.getItem());
		updateBookmarks();
	}

	/**
	 * Called when the renaming was completed. Set new name and update bookmarks
	 *
	 * @param item
	 * @param name
	 */
	@Override
	public void onNewNameSet(NavDrawerItem item, String name) {
		item.setTitle(name);

		updateBookmarks();
	}

	/**
	 * Sorts the bookmarks alphabetically
	 */
	private void sortBookmarks() {
		Collections.sort(mBookmarks, new Comparator<NavDrawerItem>() {
			@Override
			public int compare(NavDrawerItem navDrawerItem, NavDrawerItem navDrawerItem2) {
				return navDrawerItem.getTitle().compareToIgnoreCase(navDrawerItem2.getTitle());
			}
		});

		updateBookmarks();
	}

	/**
	 * Notifies the adapter of any changes, and writes the new list to the preferences
	 */
	private void updateBookmarks() {
		mAdapter.notifyDataSetChanged();

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();

		editor.putString(NavigationDrawerFragment.BOOKMARKS, GsonDrawerItem.getJson(mBookmarks));
		editor.putBoolean(NavigationDrawerFragment.BOOKMARK_CHANGED, true);
		editor.apply();
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