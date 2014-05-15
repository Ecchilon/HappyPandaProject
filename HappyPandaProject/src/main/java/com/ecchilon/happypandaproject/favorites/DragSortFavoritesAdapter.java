package com.ecchilon.happypandaproject.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.imageviewer.IMangaVisitor;
import com.ecchilon.happypandaproject.sites.test.DummyMangaItem;

/**
 * Created by Alex on 11-5-2014.
 */
public class DragSortFavoritesAdapter extends BaseAdapter {

	private FavoritesViewConstructor mConstructor;
	private FavoritesLoader mData;

	public DragSortFavoritesAdapter(FavoritesLoader loader) {
		mData = loader;
		mConstructor = new FavoritesViewConstructor();
	}

	@Override
	public int getCount() {
		return mData.getCount();
	}

	@Override
	public IMangaItem getItem(int i) {
		return mData.getFavorite(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		return mConstructor.getView(getItem(i), view, viewGroup);
	}

	private class FavoritesViewConstructor implements IMangaVisitor<View> {

		private View mConvertView;
		private ViewGroup mViewGroup;

		@Override
		public View execute(DummyMangaItem dummyMangaItem) {
			if (mConvertView == null) {
				mConvertView = LayoutInflater.from(mViewGroup.getContext())
						.inflate(R.layout.favorites_item, mViewGroup, false);
			}

			((TextView) mConvertView.findViewById(R.id.item_title)).setText(dummyMangaItem.getTitle());

			return mConvertView;
		}

		public View getView(IMangaItem item, View convertView, ViewGroup viewGroup) {
			mConvertView = convertView;
			mViewGroup = viewGroup;
			return item.visit(this);
		}
	}
}
