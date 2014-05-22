package com.ecchilon.happypandaproject.gallery;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.favorites.FavoritesLoader;
import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.imageviewer.IMangaVisitor;
import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;
import com.ecchilon.happypandaproject.sites.fakku.FakkuMangaItem;
import com.ecchilon.happypandaproject.sites.test.DummyMangaItem;
import com.ecchilon.happypandaproject.util.VolleySingleton;

/**
 * Created by Alex on 11-5-2014.
 */
public class BaseGalleryPageAdapter extends AbstractGalleryPageAdapter<IMangaItem> {

	private ViewConstructor mViewConstructor;

	public BaseGalleryPageAdapter(GalleryOverviewModuleInterface galleryInterface,
			GalleryItemClickListener itemClickListener, FavoritesLoader loader) {
		super(galleryInterface, itemClickListener, loader);

		mViewConstructor = new ViewConstructor();
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		return mViewConstructor.getView(getItem(i), view, viewGroup);
	}

	public class ViewConstructor implements IMangaVisitor<View> {

		private View mConvertView;
		private ViewGroup mViewGroup;

		@Override
		public View execute(final DummyMangaItem dummyMangaItem) {
			Context c = mViewGroup.getContext();

			if (mConvertView == null) {
				mConvertView = View.inflate(c, R.layout.dummy_gallery_item, null);
			}

			((TextView) mConvertView.findViewById(R.id.manga_title)).setText(dummyMangaItem.getTitle());

			//set all gallery item values
			if (dummyMangaItem.getThumbUrl() != null) {
				NetworkImageView networkImageView = (NetworkImageView) mConvertView.findViewById(R.id.manga_thumb);
				networkImageView.setImageUrl(dummyMangaItem.getThumbUrl(), VolleySingleton.getImageLoader());
			}

			setupFavoriteButton((ImageView) mConvertView.findViewById(R.id.manga_favorite), dummyMangaItem);

			mConvertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					getGalleryItemClickListener().GalleryItemClicked(dummyMangaItem);
				}
			});

			mConvertView.findViewById(R.id.manga_overflow).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					showOverflowView(view, null);
				}
			});

			return mConvertView;
		}

		@Override
		public View execute(final FakkuMangaItem fakkuMangaItem) {
			Context c = mViewGroup.getContext();

			if (mConvertView == null) {
				mConvertView = View.inflate(c, R.layout.dummy_gallery_item, null);
			}

			((TextView) mConvertView.findViewById(R.id.manga_title)).setText(fakkuMangaItem.getTitle());

			//set all gallery item values
			if (fakkuMangaItem.getCoverUrl() != null) {
				NetworkImageView networkImageView = (NetworkImageView) mConvertView.findViewById(R.id.manga_thumb);
				networkImageView.setImageUrl(fakkuMangaItem.getCoverUrl(), VolleySingleton.getImageLoader());
			}

			setupFavoriteButton((ImageView) mConvertView.findViewById(R.id.manga_favorite), fakkuMangaItem);

			mConvertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					getGalleryItemClickListener().GalleryItemClicked(fakkuMangaItem);
				}
			});

			mConvertView.findViewById(R.id.manga_overflow).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					showOverflowView(view, null);
				}
			});

			return mConvertView;
		}

		/**
		 * Constructs a new view
		 *
		 * @param item
		 * @param convertView
		 * @param viewGroup
		 * @return
		 */
		public View getView(IMangaItem item, View convertView, ViewGroup viewGroup) {
			mConvertView = convertView;
			mViewGroup = viewGroup;
			return item.visit(this);
		}

		/**
		 * Enables or disables the favorite button based on whether the item is already in favorites
		 *
		 * @param view
		 * @param item
		 */
		private void setupFavoriteButton(ImageView view, final IMangaItem item) {

			if (isFavorite(item)) {
				view.setImageResource(R.drawable.ic_action_favorite_disabled);
			}
			else {
				view.setImageResource(R.drawable.ic_action_favorite);
			}

			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					getGalleryItemClickListener().GalleryItemFavoriteClicked(item);
				}
			});
		}
	}
}
