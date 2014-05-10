package com.ecchilon.happypandaproject.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ecchilon.happypandaproject.GalleryPageAdapter;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.imageviewer.IMangaVisitor;
import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;
import com.ecchilon.happypandaproject.sites.test.DummyMangaItem;
import com.ecchilon.happypandaproject.storage.AlbumIndex;

/**
 * Created by Alex on 9-5-2014.
 */
public class FavoritesAdapter extends GalleryPageAdapter<IMangaItem> {

	private FavoritesViewVisitor mVisitor;

	public FavoritesAdapter(GalleryOverviewModuleInterface galleryInterface,
			GalleryItemClickListener itemClickListener) {
		super(galleryInterface, itemClickListener);

		mVisitor = new FavoritesViewVisitor();
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		mVisitor.setConvertView(view);
		mVisitor.setViewGroup(viewGroup);
		return getItem(i).visit(mVisitor);
	}

	/**
	 * Helper visitor class for constructing views
	 */
	private class FavoritesViewVisitor implements IMangaVisitor<View> {

		private View mConvertView;
		private ViewGroup mViewGroup;

		private View inflateView(int id, View convertView, ViewGroup group) {
			if (convertView != null) {
				return convertView;
			}
			else {
				return LayoutInflater.from(group.getContext()).inflate(id, group, false);
			}
		}

		//Local item, is not in favorites
		@Override
		public View execute(AlbumIndex.LocalImageViewerItem localImageViewerItem) {
			return null;
		}

		@Override
		public View execute(DummyMangaItem dummyMangaItem) {
			View dummyView = inflateView(R.layout.dummy_gallery_item, mConvertView, mViewGroup);

			dummyView.findViewById(R.id.item_favorite).setVisibility(View.GONE);
			//TODO fill view

			return dummyView;
		}

		public void setConvertView(View mConvertView) {
			this.mConvertView = mConvertView;
		}

		public void setViewGroup(ViewGroup mViewGroup) {
			this.mViewGroup = mViewGroup;
		}
	}
}
