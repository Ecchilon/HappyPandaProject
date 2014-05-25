package com.ecchilon.happypandaproject.favorites;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.imageviewer.IMangaVisitor;
import com.ecchilon.happypandaproject.sites.fakku.FakkuMangaItem;

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
		public View execute(FakkuMangaItem fakkuMangaItem) {
			//TODO implement!

			return null;
		}

		public View getView(IMangaItem item, View convertView, ViewGroup viewGroup) {
			mConvertView = convertView;
			mViewGroup = viewGroup;
			return item.visit(this);
		}
	}
}
