package com.ecchilon.happypandaproject.sites.fakku;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ecchilon.happypandaproject.sites.MangaModuleInterface;
import com.ecchilon.happypandaproject.util.VolleySingleton;

public class FakkuMangaModule implements MangaModuleInterface {
	private static final String PAGE_POSTFIX = "/read#page=";

	private FakkuManga mManga;

	private String[] mImages;

	private int mPageCount;

	private ContentChangedListener mListener;

	public FakkuMangaModule(FakkuManga manga) {
		mManga = manga;

		VolleySingleton.addRequest(new StringRequest(getPageUrl(0), new Response.Listener<String>() {
			@Override
			public void onResponse(String s) {
				String thumbString = "window.params.thumbs = [";
				int startIndex = s.indexOf(thumbString) + thumbString.length();
				int endIndex = s.indexOf("];", startIndex);
				String[] thumbs = s.substring(startIndex, endIndex).split(",");

				createImageUrlsFromThumbUrls(thumbs);

				mPageCount = thumbs.length;

				if (mListener != null) {
					mListener.contentChanged();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {

			}
		}));
	}

	@Override
	public int getPageCount() {
		return mPageCount;
	}

	@Override
	public void setContentChangedListener(ContentChangedListener listener) {
		mListener = listener;
	}

	@Override
	public void getImage(int index, GalleryImageCreatedCallback listener) {
		if (mImages != null && mImages.length > index && mImages[index] != null) {
			listener.ImageURLCreated(mImages[index]);
		}
		else {
			listener.ImageCreationFailed();
		}
	}

	private void createImageUrlsFromThumbUrls(String[] thumbs) {
		mImages = new String[thumbs.length];
		for (int i = 0; i < thumbs.length; i++) {
			mImages[i] = thumbs[i]
					.replace("\\", "")
					.replace("\"", "")
					.replace("/thumbs/", "/images/")
					.replace(".thumb", "");
		}
	}

	private String getPageUrl(int page) {
		return mManga.getUrl() + PAGE_POSTFIX + page;
	}
}
