package com.ecchilon.happypandaproject.gson;

import java.util.List;

import com.ecchilon.happypandaproject.navigation.navitems.INavPage;
import com.ecchilon.happypandaproject.sites.test.DummyNavPage;
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
		RuntimeTypeAdapterFactory<INavPage> rta = RuntimeTypeAdapterFactory.of(INavPage.class);
		rta.registerSubtype(DummyNavPage.class);

		GSON = new GsonBuilder()
				.registerTypeAdapterFactory(rta)
				.create();
	}

	public static Gson getGSON() {
		return GSON;
	}

	public static INavPage getItem(String jsonItem) {
		return GSON.fromJson(jsonItem, INavPage.class);
	}

	public static String getJson(INavPage navItem) {
		return GSON.toJson(navItem, INavPage.class);
	}

	public static String getJson(List<INavPage> navItems) {
		return GSON.toJson(navItems, new TypeToken<List<INavPage>>() {
		}.getType());
	}

	public static List<INavPage> getItems(String jsonItem) {
		return GSON.fromJson(jsonItem, new TypeToken<List<INavPage>>() {
		}.getType());
	}
}
