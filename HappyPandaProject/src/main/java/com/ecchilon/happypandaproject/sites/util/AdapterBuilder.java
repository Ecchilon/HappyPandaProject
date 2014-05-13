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
import com.ecchilon.happypandaproject.sites.test.DummyGalleryModuleInterface;
import com.ecchilon.happypandaproject.sites.test.DummyNavItem;

/**
 * Builder class for constructing the GalleryPageAdapters for in the fragments Created by Alex on 10-5-2014.
 */
public class AdapterBuilder implements INavVisitor<AbstractGalleryPageAdapter> {
	private FavoritesLoader mLoader;
	private GalleryOverviewModuleInterface<IMangaItem> mModuleInterface;
	private AbstractGalleryPageAdapter.GalleryItemClickListener mListener;

	public AdapterBuilder(Context context) {
		mLoader = new FavoritesLoader(context);
	}

	/**
	 * Sets a custom Favorites loader for retrieving and storing favorites. A default interface is constructed
	 * otherwise
	 *
	 * @param loader
	 * @return reference to self to stick true to the 'Builder' pattern
	 */
	public AdapterBuilder withFavoritesLoader(FavoritesLoader loader) {
		mLoader = loader;
		return this;
	}

	/**
	 * Sets a custom module interface for building the adapter. A default interface is constructed otherwise
	 *
	 * @param moduleInterface
	 * @return reference to self to stick true to the 'Builder' pattern
	 */
	public AdapterBuilder withModuleInterface(GalleryOverviewModuleInterface<IMangaItem> moduleInterface) {
		mModuleInterface = moduleInterface;
		return this;
	}

	/**
	 * Sets a listener for the handling clicking of the items in the fragments. Can be null.
	 *
	 * @param listener
	 * @return reference to self to stick true to the 'Builder' pattern
	 */
	public AdapterBuilder withGalleryItemListener(AbstractGalleryPageAdapter.GalleryItemClickListener listener) {
		mListener = listener;
		return this;
	}

	@Override
	public AbstractGalleryPageAdapter execute(DummyNavItem dummyNavItem) {

		if (mLoader == null) {
			throw new IllegalArgumentException("Favorites Loader can't be null!");
		}

		GalleryOverviewModuleInterface<IMangaItem> tempModuleInterface;
		if (mModuleInterface != null) {
			tempModuleInterface = mModuleInterface;
		}
		else {
			tempModuleInterface = new DummyGalleryModuleInterface();
		}

		return new BaseGalleryPageAdapter(tempModuleInterface, mListener, mLoader);
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

		return new BaseGalleryPageAdapter(tempModuleInterface, mListener, mLoader);

	}

	@Override
	public AbstractGalleryPageAdapter execute(LibraryNavPage libraryNavPage) {
		//TODO implement
		throw new UnsupportedOperationException("Oops. Better implement this quickly!");
	}
}
