package com.ecchilon.happypandaproject.sites.test;

import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.imageviewer.IMangaVisitor;

/**
 * Created by Alex on 9-5-2014.
 */
public class DummyMangaItem implements IMangaItem {

	private String mTitle;

	public DummyMangaItem(String title) {
		mTitle = title;
	}

	@Override
	public <T> T visit(IMangaVisitor<T> visitor) {
		return null;
	}

	@Override
	public String getTitle() {
		return mTitle;
	}

	public String getThumbUrl() {
		return null;
	}
}
