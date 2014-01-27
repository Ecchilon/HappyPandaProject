package com.ecchilon.happypandaproject.sites.util;

import android.os.AsyncTask;

import com.ecchilon.happypandaproject.AlbumItem;
import com.ecchilon.happypandaproject.sites.AlbumOverviewModuleInterface;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created by Alex on 1/14/14.
 */
public class StringPageLoadTask extends AsyncTask<String, Void, List<AlbumItem>> {

    public interface StringContentParser{
        public List<AlbumItem> parseContent(Document content);
    }

    private StringContentParser mParser;
    private AlbumOverviewModuleInterface.AlbumPageCreatedCallback mInterface;

    public StringPageLoadTask(StringContentParser parser, AlbumOverviewModuleInterface.AlbumPageCreatedCallback galleryInterface) {
        mParser = parser;
        mInterface = galleryInterface;
    }

    @Override
    protected List<AlbumItem> doInBackground(String... strings) {
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
    protected void onPostExecute(List<AlbumItem> albumItems) {
        if(mInterface != null)
        {
            if(albumItems != null)
                mInterface.AlbumOverviewPageCreated(albumItems);
            else
                mInterface.PageCreationFailed();
        }

    }
}
