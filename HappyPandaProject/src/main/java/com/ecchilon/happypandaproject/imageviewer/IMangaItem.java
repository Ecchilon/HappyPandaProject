package com.ecchilon.happypandaproject.imageviewer;

/**
 * Created by Alex on 1/4/14.
 */
public interface IMangaItem {
	public class LinkItem {
		private String mUrl;
		private String mName;

		public LinkItem(String mUrl, String mName) {
			this.mUrl = mUrl;
			this.mName = mName;
		}

		public String getName() {
			return mName;
		}

		public String getUrl() {
			return mUrl;
		}
	}

	<T> T visit(IMangaVisitor<T> visitor);

	String getTitle();
}
