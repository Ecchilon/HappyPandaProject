package com.ecchilon.happypandaproject.drawer;

import android.content.Context;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.gallery.navitems.FavoritesNavItem;
import com.ecchilon.happypandaproject.gallery.navitems.LibraryNavPage;
import com.ecchilon.happypandaproject.sites.test.DummyNavItem;

/**
 * Created by Alex on 9-5-2014.
 */
public class SubtitleVisitor implements INavVisitor<String> {

	private Context mAppContext;

	public SubtitleVisitor(Context context) {
		mAppContext = context.getApplicationContext();
	}

	@Override
	public String execute(DummyNavItem dummyNavItem) {
		return mAppContext.getString(R.string.page_dummy);
	}

	/**
	 * No subtitle for favorites. Null sets empty subtitle
	 *
	 * @param favoritesNavItem
	 * @return
	 */
	@Override
	public String execute(FavoritesNavItem favoritesNavItem) {
		return null;
	}

	/**
	 * Same for library
	 *
	 * @param libraryNavPage
	 * @return
	 */
	@Override
	public String execute(LibraryNavPage libraryNavPage) {
		return null;
	}
}
