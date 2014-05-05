package com.ecchilon.happypandaproject.favorites;

import java.util.List;
import java.util.zip.Inflater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.navigation.NavDrawerItem;

/**
 * Created by Alex on 5-5-2014.
 */
public class DragSortBookmarkAdapter extends BaseAdapter {

	List<NavDrawerItem> mItemList;

	public DragSortBookmarkAdapter(List<NavDrawerItem> itemList) {
		mItemList = itemList;
	}

	@Override
	public int getCount() {
		return mItemList.size();
	}

	@Override
	public NavDrawerItem getItem(int i) {
		return mItemList.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup group) {
		if(view == null) {
			view = LayoutInflater.from(group.getContext()).inflate(R.layout.bookmark_item, group, false);
		}

		((TextView)view.findViewById(R.id.bookmark_name)).setText(getItem(i).getTitle());

		return view;
	}
}
