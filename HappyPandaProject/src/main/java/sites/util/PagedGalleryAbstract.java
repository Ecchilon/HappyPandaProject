package sites.util;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ecchilon.happypandaproject.GalleryItem;

import util.PagedScrollAdapter;

/**
 * Created by Ecchilon on 1/4/14.
 */
public abstract class PagedGalleryAbstract extends PagedScrollAdapter<GalleryItem> implements GalleryOverviewInterface {

    RequestQueue queue;

    public PagedGalleryAbstract(Context context)
    {
        queue = Volley.newRequestQueue(context);

    }

    protected void SendGetRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        StringRequest request = new StringRequest(url, listener, errorListener);
        queue.add(request);
    }


    //TODO provide site tools like a user agent for the site
}
