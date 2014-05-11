package com.ecchilon.happypandaproject.favorites;

import java.util.ArrayList;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.bookmarks.UndoBarController;
import com.ecchilon.happypandaproject.gson.GsonMangaItem;
import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.mobeta.android.dslv.DragSortController;
import com.mobeta.android.dslv.DragSortListView;

public class FavoritesActivity extends ActionBarActivity implements DragSortListView.DropListener,
		DragSortListView.RemoveListener, UndoBarController.UndoListener {


	private FavoritesAdapter mAdapter;
	private FavoritesLoader mFavorites;

	private UndoBarController mUndoControl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_organize);

		DragSortListView mListView = (DragSortListView) findViewById(R.id.organize_list);
		mFavorites = new FavoritesLoader(this);

		FavoritesInterface favoritesInterface = new FavoritesInterface(mFavorites);
		mAdapter = new FavoritesAdapter(favoritesInterface, null, mFavorites);

		DragSortController controller = new DragSortController(mListView);
		controller.setDragHandleId(R.id.drag_handle);
		controller.setRemoveEnabled(true);
		controller.setDragInitMode(1);
		controller.setRemoveMode(DragSortController.FLING_REMOVE);

		mListView.setDropListener(this);
		mListView.setRemoveListener(this);
		mListView.setAdapter(mAdapter);
		mListView.setFloatViewManager(controller);
		mListView.setOnTouchListener(controller);
		mListView.setDragEnabled(true);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			mUndoControl = new UndoBarController(findViewById(R.id.undobar), this);
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.favorites, menu);
		return true;
	}

	/**
	 * Reposition bookmark in list and update preferences
	 *
	 * @param from
	 * @param to
	 */
	@Override
	public void drop(int from, int to) {
		IMangaItem item = mAdapter.getItem(from);
		mFavorites.removeFavorite(item);

		mFavorites.addFavorite(item, to);

		mAdapter.notifyDataSetChanged();
	}

	/**
	 * Remove bookmark with option to restore it through UndoBar
	 *
	 * @param i
	 */
	@Override
	public void remove(int i) {
		IMangaItem item = mAdapter.getItem(i);
		mFavorites.removeFavorite(item);
		mAdapter.notifyDataSetChanged();

		if (mUndoControl != null) {
			mUndoControl.showUndoBar(false, getString(R.string.undo_message), new MangaItemParcelable(item, i));
		}
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
				sortFavorites();
				break;
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void sortFavorites() {
		ArrayList<IMangaItem> favorites = mFavorites.getFavorites();
	}

	@Override
	public void onUndo(Parcelable token) {
		MangaItemParcelable parcelable = (MangaItemParcelable) token;
		mFavorites.addFavorite(parcelable.getItem(), parcelable.getPosition());
		mAdapter.notifyDataSetChanged();
	}

	/**
	 * Helper class to store the item as a parcelable
	 */
	private static class MangaItemParcelable implements Parcelable {

		private IMangaItem mItem;
		private int mPosition;

		public IMangaItem getItem() {
			return mItem;
		}

		public int getPosition() {
			return mPosition;
		}

		public MangaItemParcelable(IMangaItem item, int position) {
			mItem = item;
			mPosition = position;
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel parcel, int i) {
			parcel.writeString(GsonMangaItem.getJson(mItem));
			parcel.writeInt(mPosition);
		}

		public static final Parcelable.Creator<MangaItemParcelable> CREATOR
				= new Parcelable.Creator<MangaItemParcelable>() {
			public MangaItemParcelable createFromParcel(Parcel in) {
				return new MangaItemParcelable(in);
			}

			public MangaItemParcelable[] newArray(int size) {
				return new MangaItemParcelable[size];
			}
		};

		private MangaItemParcelable(Parcel in) {
			mItem = GsonMangaItem.getItem(in.readString());
			mPosition = in.readInt();
		}
	}
}
