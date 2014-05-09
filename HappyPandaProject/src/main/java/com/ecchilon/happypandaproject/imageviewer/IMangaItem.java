package com.ecchilon.happypandaproject.imageviewer;

/**
 * Created by Alex on 1/4/14.
 */
public interface IMangaItem {
	<T> T visit(IMangaVisitor<T> visitor);

	String getTitle();
}
