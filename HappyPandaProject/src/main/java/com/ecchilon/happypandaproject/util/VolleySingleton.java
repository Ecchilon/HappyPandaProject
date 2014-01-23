package com.ecchilon.happypandaproject.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Alex on 1/23/14.
 */
public class VolleySingleton {

    private static ImageLoader mImageLoader;
    private static RequestQueue mRequestQueue;
    private static int mNavigationPositionIndex = -1;

    public static ImageLoader getImageLoader() { return mImageLoader; }

    public VolleySingleton(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }

    /**
     * Adds a new requests for the currently active module.
     * @param request The request to be added
     */
    public static void addRequest(Request request) {
        if(mRequestQueue != null) {
            request.setTag(mNavigationPositionIndex);
            mRequestQueue.add(request);
        }
    }

    /**
     * Stops all requests for the currently active module.
     */
    public static void cancelRequests() {
        if(mRequestQueue != null)
            mRequestQueue.cancelAll(mNavigationPositionIndex);
    }

    public void setNavigationPositionIndex(int index) {
        mNavigationPositionIndex = index;
    }

    /**
     * Stops ALL outgoing requests
     */
    public void cancelAllRequests() {
        if(mRequestQueue != null) {
            mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
        }
    }
}
