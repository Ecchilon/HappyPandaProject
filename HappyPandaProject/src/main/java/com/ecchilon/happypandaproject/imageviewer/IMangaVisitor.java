package com.ecchilon.happypandaproject.imageviewer;

import com.ecchilon.happypandaproject.sites.fakku.FakkuMangaItem;

/**
 * Created by Alex on 9-5-2014.
 */
public interface IMangaVisitor<T> {
	T execute(FakkuMangaItem fakkuMangaItem);
}
