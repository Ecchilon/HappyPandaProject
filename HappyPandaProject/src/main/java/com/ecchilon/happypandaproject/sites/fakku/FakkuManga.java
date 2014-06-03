package com.ecchilon.happypandaproject.sites.fakku;

import java.util.List;

import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.imageviewer.IMangaVisitor;

/**
 * Created by Alex on 22-5-2014.
 */
public class FakkuManga implements IMangaItem {

	private FakkuNavItem mTitle;
	private FakkuNavItem mSeries;
	private String mCoverUrl;
	private List<FakkuNavItem> mArtists;
	private List<FakkuNavItem> mTags;

	public FakkuManga(FakkuNavItem title, String coverUrl, FakkuNavItem series,
			List<FakkuNavItem> artists, List<FakkuNavItem> tags) {
		mTitle = title;
		mCoverUrl = coverUrl;
		mSeries = series;
		mArtists = artists;
		mTags = tags;
	}

	public FakkuNavItem getSeries() {
		return mSeries;
	}

	public String getUrl() {
		return mTitle.getUrl();
	}

	public List<FakkuNavItem> getTags() {
		return mTags;
	}

	public List<FakkuNavItem> getArtists() {
		return mArtists;
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
		return mTitle.getTitle();
	}
}
