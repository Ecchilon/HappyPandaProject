package com.ecchilon.happypandaproject.gson;

import java.util.List;

import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.sites.fakku.FakkuManga;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Alex on 9-5-2014.
 */
public class GsonMangaItem {
	private static Gson GSON;

	//TODO find an acceptable way around this ugly code
	static {
		RuntimeTypeAdapterFactory<IMangaItem> rta = RuntimeTypeAdapterFactory.of(IMangaItem.class);
		rta.registerSubtype(FakkuManga.class);

		GSON = new GsonBuilder()
				.registerTypeAdapterFactory(rta)
				.create();
	}

	public static IMangaItem getItem(String jsonItem) {
		return GSON.fromJson(jsonItem, IMangaItem.class);
	}

	public static String getJson(IMangaItem navItem) {
		return GSON.toJson(navItem, IMangaItem.class);
	}

	public static String getJson(List<IMangaItem> navItems) {
		return GSON.toJson(navItems, new TypeToken<List<IMangaItem>>() {
		}.getType());
	}

	public static List<IMangaItem> getItems(String jsonItem) {
		return GSON.fromJson(jsonItem, new TypeToken<List<IMangaItem>>() {
		}.getType());
	}
}
