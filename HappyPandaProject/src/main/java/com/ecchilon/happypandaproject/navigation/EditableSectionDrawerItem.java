package com.ecchilon.happypandaproject.navigation;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Alex on 5-5-2014.
 */
public class EditableSectionDrawerItem implements IDrawerItem {
	private String mTitle;
	private int mDrawableId;
	private View.OnClickListener mListener;

	public EditableSectionDrawerItem(String title, int drawableId, View.OnClickListener listener) {
		mTitle = title;
		mDrawableId = drawableId;
		mListener = listener;
	}

	@Override
	public <T> T visit(NavigationDrawerAdapter.IDrawerVisitor<T> visitor, View convertView, ViewGroup group) {
		return visitor.execute(this, convertView, group);
	}

	@Override
	public int getViewType() {
		return 2;
	}

	@Override
	public boolean isFragmentDisplay() {
		return false;
	}

	public String getTitle() {
		return mTitle;
	}

	public View.OnClickListener getListener() {
		return mListener;
	}
}
