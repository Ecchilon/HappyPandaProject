package com.ecchilon.happypandaproject.gson;

import java.util.List;

import com.ecchilon.happypandaproject.navigation.NavDrawerItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Alex on 5-5-2014.
 */
public class GsonDrawerItem {

	public static NavDrawerItem getItem(String jsonItem) {
		return GsonNavItem.getGSON().fromJson(jsonItem, NavDrawerItem.class);
	}

	public static String getJson(NavDrawerItem item) {
		return GsonNavItem.getGSON().toJson(item, NavDrawerItem.class);
	}

	public static List<NavDrawerItem> getItems(String jsonItem) {
		return GsonNavItem.getGSON().fromJson(jsonItem, new TypeToken<List<NavDrawerItem>>() {
		}.getType());
	}

	public static String getJson(List<NavDrawerItem> items) {
		return GsonNavItem.getGSON().toJson(items, new TypeToken<List<NavDrawerItem>>() {
		}.getType());
	}
}
