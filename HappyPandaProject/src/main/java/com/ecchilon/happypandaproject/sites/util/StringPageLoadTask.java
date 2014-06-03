package com.ecchilon.happypandaproject.sites.util;

import java.util.List;

import android.os.AsyncTask;
import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by Alex on 1/14/14.
 */
public class StringPageLoadTask extends AsyncTask<String, Void, List<IMangaItem>> {

	public interface StringContentParser {
		public List<IMangaItem> parseContent(Document content);
	}

	private StringContentParser mParser;
	private GalleryOverviewModuleInterface.GalleryPageCreatedCallback mInterface;

	public StringPageLoadTask(StringContentParser parser,
			GalleryOverviewModuleInterface.GalleryPageCreatedCallback galleryInterface) {
		mParser = parser;
		mInterface = galleryInterface;
	}

	@Override
	protected List<IMangaItem> doInBackground(String... strings) {
		int startIndex = strings[0].indexOf("<body");
		int endIndex = strings[0].lastIndexOf("</body>");

		Document doc = Jsoup.parse(strings[0].substring(startIndex, endIndex + 7));

		if (mParser != null) {
			return mParser.parseContent(doc);
		}
		else {
			return null;
		}
	}

	@Override
	protected void onPostExecute(List<IMangaItem> galleryItems) {
		if (mInterface != null) {
			if (galleryItems != null) {
				mInterface.GalleryOverviewPageCreated(galleryItems);
			}
			else {
				mInterface.PageCreationFailed();
			}
		}

	}
}
