package com.ecchilon.happypandaproject.imageviewer;

import com.ecchilon.happypandaproject.sites.test.DummyMangaItem;
import com.ecchilon.happypandaproject.storage.AlbumIndex;

/**
 * Created by Alex on 9-5-2014.
 */
public interface IMangaVisitor<T> {
	T execute(AlbumIndex.LocalImageViewerItem localImageViewerItem);

	T execute(DummyMangaItem dummyMangaItem);
}
