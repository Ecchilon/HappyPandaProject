package com.ecchilon.happypandaproject;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import com.ecchilon.happypandaproject.bookmarks.UndoBarController;
import com.mobeta.android.dslv.DragSortController;
import com.mobeta.android.dslv.DragSortListView;


public abstract class OrganizeActivity extends ActionBarActivity implements DragSortListView.DropListener,
		DragSortListView.RemoveListener, UndoBarController.UndoListener {

	private BaseAdapter mAdapter;
	private UndoBarController mUndoControl;
	private AdapterView.OnItemClickListener mListClickListener;
	private DragSortListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_organize);

		mListView = (DragSortListView) findViewById(R.id.organize_list);
		mAdapter = getAdapter();

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

		if (mListClickListener != null) {
			mListView.setOnItemClickListener(mListClickListener);
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			mUndoControl = new UndoBarController(findViewById(R.id.undobar), this);
		}
	}

	public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
		mListClickListener = listener;

		if (mListView != null) {
			mListView.setOnItemClickListener(mListClickListener);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.organize, menu);
		return true;
	}

	@Override
	public final void remove(int which) {
		Parcelable p = getParcelable(which);
		removeItem(which);

		mAdapter.notifyDataSetChanged();

		if (mUndoControl != null) {
			mUndoControl.showUndoBar(false, getString(getUndoMessageID()), p);
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
				sortItems();
				mAdapter.notifyDataSetChanged();
				break;
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public final void drop(int from, int to) {
		moveItem(from, to);

		mAdapter.notifyDataSetChanged();
	}

	@Override
	public final void onUndo(Parcelable token) {
		restoreItem(token);

		mAdapter.notifyDataSetChanged();
	}

	protected final void shouldUpdateView() {
		if (mAdapter != null) {
			mAdapter.notifyDataSetChanged();
		}
	}

	protected abstract BaseAdapter getAdapter();

	protected abstract void moveItem(int from, int to);

	protected abstract void removeItem(int which);

	protected abstract void restoreItem(Parcelable parcelable);

	protected abstract void sortItems();

	protected abstract int getUndoMessageID();

	protected abstract Parcelable getParcelable(int which);
}
