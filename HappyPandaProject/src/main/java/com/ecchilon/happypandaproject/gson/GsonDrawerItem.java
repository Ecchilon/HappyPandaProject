package com.ecchilon.happypandaproject.gson;

import java.util.List;

import com.ecchilon.happypandaproject.navigation.NavDrawerPage;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Alex on 5-5-2014.
 */
public class GsonDrawerItem {

	public static NavDrawerPage getItem(String jsonItem) {
		return GsonNavItem.getGSON().fromJson(jsonItem, NavDrawerPage.class);
	}

	public static String getJson(NavDrawerPage item) {
		return GsonNavItem.getGSON().toJson(item, NavDrawerPage.class);
	}

	public static List<NavDrawerPage> getItems(String jsonItem) {
		return GsonNavItem.getGSON().fromJson(jsonItem, new TypeToken<List<NavDrawerPage>>() {
		}.getType());
	}

	public static String getJson(List<NavDrawerPage> items) {
		return GsonNavItem.getGSON().toJson(items, new TypeToken<List<NavDrawerPage>>() {
		}.getType());
	}
}
