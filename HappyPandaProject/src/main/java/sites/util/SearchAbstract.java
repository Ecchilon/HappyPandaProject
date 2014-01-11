package sites.util;

import android.content.Context;

/**
 * Created by Ecchilon on 1/4/14.
 */
public abstract class SearchAbstract extends PagedGalleryAbstract {

    public SearchAbstract(Context context){
        super(context);
    }

    String mQuery;

    public void setQuery(String query) {
        mQuery = query;
    }

    public String getQuery() {
        return  mQuery;
    }
}
