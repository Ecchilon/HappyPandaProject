package com.ecchilon.happypandaproject.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.gallery.AbstractGalleryPageAdapter;
import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.imageviewer.IMangaVisitor;
import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;
import com.ecchilon.happypandaproject.sites.test.DummyMangaItem;
import com.ecchilon.happypandaproject.util.VolleySingleton;

/**
 * Created by Alex on 9-5-2014.
 */
public class FavoritesAdapter extends AbstractGalleryPageAdapter<IMangaItem> {

	private FavoritesViewConstructor mVisitor;

	public FavoritesAdapter(GalleryOverviewModuleInterface galleryInterface,
			GalleryItemClickListener itemClickListener, FavoritesLoader favoritesLoader) {
		super(galleryInterface, itemClickListener, favoritesLoader);

		mVisitor = new FavoritesViewConstructor();
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
	private class FavoritesViewConstructor implements IMangaVisitor<View> {

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

		@Override
		public View execute(final DummyMangaItem dummyMangaItem) {
			View dummyView = inflateView(R.layout.dummy_gallery_item, mConvertView, mViewGroup);

			dummyView.findViewById(R.id.item_favorite).setVisibility(View.GONE);

			((TextView) dummyView.findViewById(R.id.item_title)).setText(dummyMangaItem.getTitle());

			//set all gallery item values
			if (dummyMangaItem.getThumbUrl() != null) {
				NetworkImageView networkImageView = (NetworkImageView) dummyView.findViewById(R.id.item_thumb);
				networkImageView.setImageUrl(dummyMangaItem.getThumbUrl(), VolleySingleton.getImageLoader());
			}

			dummyView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					getGalleryItemClickListener().GalleryItemClicked(dummyMangaItem);
				}
			});

			dummyView.findViewById(R.id.item_overflow).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					//TODO build a simple overflow wrapper for the items
					//showOverflowView(view, dummyNavItems);
				}
			});

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
