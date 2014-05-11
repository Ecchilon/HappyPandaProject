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
		return visitor.execute(this);
	}

	@Override
	public String getTitle() {
		return mTitle;
	}

	public String getThumbUrl() {
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof DummyMangaItem)) {
			return false;
		}

		DummyMangaItem other = (DummyMangaItem) o;

		return this.mTitle.equals(other.mTitle);
	}
}
