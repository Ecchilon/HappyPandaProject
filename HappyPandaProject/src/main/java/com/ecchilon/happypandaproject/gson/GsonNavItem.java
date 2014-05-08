package com.ecchilon.happypandaproject.gson;

import java.util.List;

import com.ecchilon.happypandaproject.sites.test.DummyNavItem;
import com.ecchilon.happypandaproject.navigation.navitems.INavItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Alex on 5/4/2014.
 */
public class GsonNavItem {
	private static Gson GSON;
	//TODO find an acceptable way around this ugly code
	static {
		RuntimeTypeAdapterFactory<INavItem> rta = RuntimeTypeAdapterFactory.of(INavItem.class);
		rta.registerSubtype(DummyNavItem.class);

		GSON = new GsonBuilder()
				.registerTypeAdapterFactory(rta)
				.create();
	}

	public static Gson getGSON() {
		return GSON;
	}

	public static INavItem getItem(String jsonItem) {
		return GSON.fromJson(jsonItem, INavItem.class);
	}

	public static String getJson(INavItem navItem) {
		return GSON.toJson(navItem, INavItem.class);
	}

	public static String getJson(List<INavItem> navItems) {
		return GSON.toJson(navItems, new TypeToken<List<INavItem>>() {
		}.getType());
	}

	public static List<INavItem> getItems(String jsonItem) {
		return GSON.fromJson(jsonItem, new TypeToken<List<INavItem>>() {
		}.getType());
	}
}
