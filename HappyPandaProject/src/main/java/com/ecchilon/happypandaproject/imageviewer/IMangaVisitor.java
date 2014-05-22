package com.ecchilon.happypandaproject.imageviewer;

import com.ecchilon.happypandaproject.sites.fakku.FakkuMangaItem;
import com.ecchilon.happypandaproject.sites.test.DummyMangaItem;

/**
 * Created by Alex on 9-5-2014.
 */
public interface IMangaVisitor<T> {
	T execute(DummyMangaItem dummyMangaItem);

	T execute(FakkuMangaItem fakkuMangaItem);
}
