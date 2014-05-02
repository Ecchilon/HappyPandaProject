package com.ecchilon.happypandaproject.navigation;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Alex on 12-4-2014.
 */
public interface IDrawerItem {
	<T> T visit(NavigationDrawerAdapter.IDrawerVisitor<T> visitor, View convertView, ViewGroup group);
	int getViewType();
}
