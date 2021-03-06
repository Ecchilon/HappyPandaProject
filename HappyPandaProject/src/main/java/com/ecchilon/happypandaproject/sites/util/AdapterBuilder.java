package com.ecchilon.happypandaproject.sites.util;

import android.content.Context;
import com.ecchilon.happypandaproject.drawer.INavVisitor;
import com.ecchilon.happypandaproject.favorites.FavoritesInterface;
import com.ecchilon.happypandaproject.favorites.FavoritesLoader;
import com.ecchilon.happypandaproject.gallery.AbstractGalleryPageAdapter;
import com.ecchilon.happypandaproject.gallery.BaseGalleryPageAdapter;
import com.ecchilon.happypandaproject.gallery.navitems.FavoritesNavItem;
import com.ecchilon.happypandaproject.gallery.navitems.LibraryNavPage;
import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;
import com.ecchilon.happypandaproject.sites.fakku.FakkuContentModule;
import com.ecchilon.happypandaproject.sites.fakku.FakkuContentParser;
import com.ecchilon.happypandaproject.sites.fakku.FakkuNavItem;

/**
 * Builder class for constructing the GalleryPageAdapters for in the fragments Created by Alex on 10-5-2014.
 */
public class AdapterBuilder implements INavVisitor<AbstractGalleryPageAdapter> {
	private FavoritesLoader mLoader;
	private GalleryOverviewModuleInterface<IMangaItem> mModuleInterface;
	private AbstractGalleryPageAdapter.GalleryItemClickListener mListener;
	private Context mAppContext;

	public AdapterBuilder(Context context) {
		mAppContext = context.getApplicationContext();
		mLoader = new FavoritesLoader(mAppContext);
	}

	/**
	 * Sets a custom Favorites loader for retrieving and storing favorites. A default interface is constructed
	 * otherwise
	 *
	 * @param loader
	 * @return reference to self for chaining
	 */
	public AdapterBuilder withFavoritesLoader(FavoritesLoader loader) {
		mLoader = loader;
		return this;
	}

	/**
	 * Sets a custom module interface for building the adapter. A default interface is constructed otherwise
	 *
	 * @param moduleInterface
	 * @return reference to self for chaining
	 */
	public AdapterBuilder withModuleInterface(GalleryOverviewModuleInterface<IMangaItem> moduleInterface) {
		mModuleInterface = moduleInterface;
		return this;
	}

	/**
	 * Sets a listener for the handling clicking of the items in the fragments. Can be null.
	 *
	 * @param listener
	 * @return reference to self for chaining
	 */
	public AdapterBuilder withGalleryItemListener(AbstractGalleryPageAdapter.GalleryItemClickListener listener) {
		mListener = listener;
		return this;
	}

	@Override
	public AbstractGalleryPageAdapter execute(FavoritesNavItem favoritesNavItem) {

		if (mLoader == null) {
			throw new IllegalArgumentException("Favorites Loader can't be null!");
		}

		GalleryOverviewModuleInterface<IMangaItem> tempModuleInterface;
		if (mModuleInterface != null) {
			tempModuleInterface = mModuleInterface;
		}
		else {
			tempModuleInterface = new FavoritesInterface(mLoader);
		}

		return new BaseGalleryPageAdapter(tempModuleInterface, mListener, mLoader, mAppContext);

	}

	@Override
	public AbstractGalleryPageAdapter execute(LibraryNavPage libraryNavPage) {
		//TODO implement
		throw new UnsupportedOperationException("Oops. Better implement this quickly!");
	}

	@Override
	public AbstractGalleryPageAdapter execute(FakkuNavItem fakkuNavItem) {
		if (mLoader == null) {
			throw new IllegalArgumentException("Favorites Loader can't be null!");
		}

		FakkuContentModule contentModule = new FakkuContentModule(fakkuNavItem.getUrl());
		contentModule.setStringContentParser(new FakkuContentParser());

		return new BaseGalleryPageAdapter(contentModule, mListener, mLoader, mAppContext);
	}
}
