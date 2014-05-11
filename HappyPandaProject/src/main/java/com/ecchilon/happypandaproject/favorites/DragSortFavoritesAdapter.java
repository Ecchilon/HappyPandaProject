package com.ecchilon.happypandaproject.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.gallery.BaseGalleryPageAdapter;
import com.ecchilon.happypandaproject.imageviewer.IMangaVisitor;
import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;
import com.ecchilon.happypandaproject.sites.test.DummyMangaItem;

/**
 * Created by Alex on 11-5-2014.
 */
public class DragSortFavoritesAdapter extends BaseGalleryPageAdapter {

	private PageTypeFinder mTypeFinder;

	public DragSortFavoritesAdapter(GalleryOverviewModuleInterface galleryInterface,
			GalleryItemClickListener itemClickListener, FavoritesLoader loader) {
		super(galleryInterface, itemClickListener, loader);

		mTypeFinder = new PageTypeFinder();
	}

	@Override
	public int getItemViewType(int position) {
		return getItem(position).visit(mTypeFinder);
	}

	/**
	 * TODO should be implemented better to allow easy extension Number of types based on the number of subclasses of
	 * IMangaItem
	 *
	 * @return
	 */
	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		if (view == null) {
			view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favorite_container, viewGroup, false);
			view.setEnabled(false);
		}

		FrameLayout container = (FrameLayout) view.findViewById(R.id.frame_container);
		View favoriteView;

		if (container.getChildCount() == 0) {
			favoriteView = super.getView(i, null, container);
		}
		else {
			favoriteView = super.getView(i, container.getChildAt(0), container);
		}

		if (favoriteView != null) {
			View overflow = favoriteView.findViewById(R.id.item_overflow);
			if (overflow != null) {
				overflow.setVisibility(View.GONE);
			}

			View favorite = favoriteView.findViewById(R.id.item_favorite);
			if (favorite != null) {
				favorite.setVisibility(View.GONE);
			}

			container.addView(favoriteView);
		}
		return view;
	}

	/**
	 * Returns a unique type based on the MangaItem so that the views can be recycled properly
	 */
	private class PageTypeFinder implements IMangaVisitor<Integer> {

		@Override
		public Integer execute(DummyMangaItem dummyMangaItem) {
			return 0;
		}
	}
}
