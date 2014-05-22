package com.ecchilon.happypandaproject.sites.fakku;

import java.util.ArrayList;
import java.util.List;

import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.sites.util.StringPageLoadTask;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Alex on 22-5-2014.
 */
public class FakkuContentParser implements StringPageLoadTask.StringContentParser {
	private static final String DOMAIN_URL = "http://www.fakku.net";

	@Override
	public List<IMangaItem> parseContent(Document content) {
		Elements elements = content.getElementsByAttributeValueContaining("class", "content-row ");

		List<IMangaItem> fakkuMangaList = new ArrayList<IMangaItem>();

		for (Element item : elements) {
			String coverUrl = item.getElementsByAttributeValue("class", "cover").first().attr("src");

			Element titleElement = item.getElementsByAttributeValue("class", "content-title").first();

			String title = titleElement.attr("title");

			String link = DOMAIN_URL + titleElement.attr("href");

			//TODO fill overflow and other display values!

			fakkuMangaList.add(new FakkuMangaItem(title, link, coverUrl));
		}

		return fakkuMangaList;
	}
}
