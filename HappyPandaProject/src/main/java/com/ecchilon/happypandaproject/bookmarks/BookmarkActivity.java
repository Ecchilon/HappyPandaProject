package com.ecchilon.happypandaproject.bookmarks;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import com.ecchilon.happypandaproject.OrganizeActivity;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.drawer.NavDrawerFactory;
import com.ecchilon.happypandaproject.drawer.NavDrawerItem;
import com.ecchilon.happypandaproject.drawer.NavigationDrawerFragment;
import com.ecchilon.happypandaproject.gson.GsonDrawerItem;

/**
 * Activity that allows the user to organize their bookmarks, if they have any. Created by Alex on 5/6/2014.
 */
public class BookmarkActivity extends OrganizeActivity implements
		RenameDialogFragment.RenameListener, AdapterView.OnItemClickListener {

	private List<NavDrawerItem> mBookmarks;

	/**
	 * Constructs the DragSortListView
	 *
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mBookmarks = NavDrawerFactory.loadBookmarks(this);
		setOnItemClickListener(this);
		super.onCreate(savedInstanceState);
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
		fragment.setItem(mBookmarks.get(i));
		fragment.show(getSupportFragmentManager(), "RENAME");
	}

	@Override
	protected BaseAdapter getAdapter() {
		return new DragSortBookmarkAdapter(mBookmarks);
	}

	@Override
	protected void moveItem(int from, int to) {
		NavDrawerItem movedItem = mBookmarks.remove(from);
		mBookmarks.add(to, movedItem);

		updateBookmarks();
	}

	@Override
	protected void removeItem(int which) {
		mBookmarks.remove(which);

		updateBookmarks();
	}

	@Override
	protected void restoreItem(Parcelable token) {
		NavDrawerParcelable parcelable = (NavDrawerParcelable) token;
		mBookmarks.add(parcelable.getPosition(), parcelable.getItem());
		updateBookmarks();
	}

	@Override
	protected void sortItems() {
		Collections.sort(mBookmarks, new Comparator<NavDrawerItem>() {
			@Override
			public int compare(NavDrawerItem navDrawerItem, NavDrawerItem navDrawerItem2) {
				return navDrawerItem.getTitle().compareToIgnoreCase(navDrawerItem2.getTitle());
			}
		});

		updateBookmarks();
	}

	@Override
	protected int getUndoMessageID() {
		return R.string.undo_message;
	}

	@Override
	protected Parcelable getParcelable(int which) {
		return new NavDrawerParcelable(mBookmarks.get(which), which);
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
		shouldUpdateView();
	}

	/**
	 * Notifies the adapter of any changes, and writes the new list to the preferences
	 */
	private void updateBookmarks() {
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
