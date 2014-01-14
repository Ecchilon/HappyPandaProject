package sites;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.ecchilon.happypandaproject.GalleryOverview;
import org.json.JSONObject;

import sites.util.StringPageLoadTask;
import sites.util.StringPageLoadTask.StringContentParser;

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

        sendGetStringRequest(getUrl(index), new Response.Listener<String>() {
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
                });
    }

    public abstract String getUrl(int index);

    protected void sendGetStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        StringRequest request = new StringRequest(url, listener, errorListener);
        GalleryOverview.getRequestQueue().add(request);
    }

    protected void sendGetJSONRequest(String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener)
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        GalleryOverview.getRequestQueue().add(request);
    }

    //TODO provide site tools like a user agent for the site
}
