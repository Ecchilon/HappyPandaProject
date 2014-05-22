package com.ecchilon.happypandaproject.sites.fakku;

import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.imageviewer.IMangaVisitor;

/**
 * Created by Alex on 22-5-2014.
 */
public class FakkuMangaItem implements IMangaItem {

	private String mUrl;
	private String mName;
	private String mCoverUrl;

	public FakkuMangaItem(String name, String url, String coverUrl) {
		mName = name;
		mUrl = url;
		mCoverUrl = coverUrl;
	}

	public String getName() {
		return mName;
	}

	public String getUrl() {
		return mUrl;
	}

	public String getCoverUrl() {
		return mCoverUrl;
	}

	@Override
	public <T> T visit(IMangaVisitor<T> visitor) {
		return visitor.execute(this);
	}

	@Override
	public String getTitle() {
		return mName;
	}
}
