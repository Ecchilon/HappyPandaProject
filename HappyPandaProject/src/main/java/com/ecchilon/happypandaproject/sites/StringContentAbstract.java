package com.ecchilon.happypandaproject.sites;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ecchilon.happypandaproject.GalleryOverviewActivity;

import com.ecchilon.happypandaproject.sites.util.StringPageLoadTask;
import com.ecchilon.happypandaproject.sites.util.StringPageLoadTask.StringContentParser;

/**
 * Created by Alex on 1/4/14.
 */
public abstract class StringContentAbstract implements GalleryOverviewInterface {

    public StringContentParser mContentParser;

    public void setStringContentParser(StringContentParser parser) { mContentParser = parser; }

    @Override
    public void getPage(int index, final GalleryPageCreatedCallback listener) {
        // StringContentParser should always be set
        if(mContentParser == null){
            listener.PageCreationFailed();
            return;
        }

        //adds the string request to the queue.
        GalleryOverviewActivity.addRequest(new StringRequest(getUrl(index), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //since onResponse is handled on the UIThread, create a new task to prevent the UI from stalling
                new StringPageLoadTask(mContentParser, listener).execute(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.PageCreationFailed();
            }
        }
        ));
    }

    public abstract String getUrl(int index);
}
