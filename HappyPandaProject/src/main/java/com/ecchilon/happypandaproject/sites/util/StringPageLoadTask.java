package com.ecchilon.happypandaproject.sites.util;

import android.os.AsyncTask;

import com.ecchilon.happypandaproject.GalleryItem;
import com.ecchilon.happypandaproject.sites.GalleryOverviewModuleInterface;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created by Alex on 1/14/14.
 */
public class StringPageLoadTask extends AsyncTask<String, Void, List<GalleryItem>> {

    public interface StringContentParser{
        public List<GalleryItem> parseContent(Document content);
    }

    private StringContentParser mParser;
    private GalleryOverviewModuleInterface.GalleryPageCreatedCallback mInterface;

    public StringPageLoadTask(StringContentParser parser, GalleryOverviewModuleInterface.GalleryPageCreatedCallback galleryInterface) {
        mParser = parser;
        mInterface = galleryInterface;
    }

    @Override
    protected List<GalleryItem> doInBackground(String... strings) {
        int startIndex = strings[0].indexOf("<body");
        int endIndex = strings[0].lastIndexOf("</body>");

        Document doc = Jsoup.parse(strings[0].substring(startIndex, endIndex + 7));

        //TODO consider try-catching the parser, since this is the actual 'external' part of the application
        if(mParser != null){
            return mParser.parseContent(doc);
        }
        else
            return null;
    }

    @Override
    protected void onPostExecute(List<GalleryItem> galleryItems) {
        if(mInterface != null)
        {
            if(galleryItems != null)
                mInterface.GalleryOverviewPageCreated(galleryItems);
            else
                mInterface.PageCreationFailed();
        }

    }
}
