package com.ecchilon.happypandaproject.navigation;

import android.content.Context;
import com.ecchilon.happypandaproject.R;
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
}
