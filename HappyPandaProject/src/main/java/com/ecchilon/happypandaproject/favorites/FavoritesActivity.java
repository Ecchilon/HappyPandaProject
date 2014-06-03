package com.ecchilon.happypandaproject.favorites;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Menu;
import android.widget.BaseAdapter;
import com.ecchilon.happypandaproject.OrganizeActivity;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.gson.GsonMangaItem;
import com.ecchilon.happypandaproject.imageviewer.IMangaItem;

public class FavoritesActivity extends OrganizeActivity {

	private FavoritesLoader mFavorites;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mFavorites = new FavoritesLoader(this);

		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.favorites, menu);
		return true;
	}

	@Override
	protected BaseAdapter getAdapter() {
		return new DragSortFavoritesAdapter(mFavorites);
	}

	@Override
	protected void moveItem(int from, int to) {
		IMangaItem item = mFavorites.getFavorite(from);
		mFavorites.removeFavorite(item);
		mFavorites.addFavorite(item, to);
	}

	@Override
	protected void removeItem(int which) {
		IMangaItem item = mFavorites.getFavorite(which);
		mFavorites.removeFavorite(item);
	}

	@Override
	protected void restoreItem(Parcelable parcelable) {
		MangaItemParcelable mangaItemParcelable = (MangaItemParcelable) parcelable;

		mFavorites.addFavorite(mangaItemParcelable.getItem(), mangaItemParcelable.getPosition());
	}

	@Override
	protected void sortItems() {
		ArrayList<IMangaItem> favorites = mFavorites.getFavorites();

		Collections.sort(favorites, new Comparator<IMangaItem>() {
			@Override
			public int compare(IMangaItem o, IMangaItem o2) {
				return o.getTitle().compareTo(o2.getTitle());
			}
		});

		mFavorites.setFavorites(favorites);
	}

	@Override
	protected int getUndoMessageID() {
		return R.string.undo_message_favorite;
	}

	@Override
	protected Parcelable getParcelable(int which) {
		return new MangaItemParcelable(mFavorites.getFavorite(which), which);
	}

	/**
	 * Helper class to store the item as a parcelable
	 */
	private static class MangaItemParcelable implements Parcelable {

		private IMangaItem mItem;
		private int mPosition;

		public IMangaItem getItem() {
			return mItem;
		}

		public int getPosition() {
			return mPosition;
		}

		public MangaItemParcelable(IMangaItem item, int position) {
			mItem = item;
			mPosition = position;
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel parcel, int i) {
			parcel.writeString(GsonMangaItem.getJson(mItem));
			parcel.writeInt(mPosition);
		}

		public static final Parcelable.Creator<MangaItemParcelable> CREATOR
				= new Parcelable.Creator<MangaItemParcelable>() {
			public MangaItemParcelable createFromParcel(Parcel in) {
				return new MangaItemParcelable(in);
			}

			public MangaItemParcelable[] newArray(int size) {
				return new MangaItemParcelable[size];
			}
		};

		private MangaItemParcelable(Parcel in) {
			mItem = GsonMangaItem.getItem(in.readString());
			mPosition = in.readInt();
		}
	}
}
