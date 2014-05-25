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
	private static final String BASE_PAGE = "/page/";

	@Override
	public List<IMangaItem> parseContent(Document content) {
		Elements elements = content.getElementsByAttributeValueContaining("class", "content-row ");

		List<IMangaItem> fakkuMangaList = new ArrayList<IMangaItem>();

		for (Element item : elements) {
			String coverUrl = item.getElementsByAttributeValue("class", "cover").first().attr("src");

			Element titleElement = item.getElementsByAttributeValue("class", "content-title").first();

			FakkuNavItem title =
					new FakkuNavItem(titleElement.attr("title"), DOMAIN_URL + titleElement.attr("href"));

			Element seriesElement = item.getElementsByAttributeValueStarting("href", "/series/").first();
			FakkuNavItem series =
					new FakkuNavItem(seriesElement.text(), DOMAIN_URL + seriesElement.attr("href") + BASE_PAGE);

			Elements artistElementList = item.getElementsByAttributeValueStarting("href", "/artists/");
			List<FakkuNavItem> artists = new ArrayList<FakkuNavItem>();
			for (Element artistElement : artistElementList) {
				artists.add(
						new FakkuNavItem(artistElement.text(), DOMAIN_URL + artistElement.attr("href") + BASE_PAGE));
			}

			Elements tagsElementList = item.getElementsByAttributeValueStarting("href", "/tags/");
			List<FakkuNavItem> tags = new ArrayList<FakkuNavItem>();
			for (Element tagElement : tagsElementList) {
				tags.add(new FakkuNavItem(tagElement.text(), DOMAIN_URL + tagElement.attr("href") + BASE_PAGE));
			}

			fakkuMangaList.add(new FakkuMangaItem(title, coverUrl, series, artists, tags));
		}

		return fakkuMangaList;
	}
}
