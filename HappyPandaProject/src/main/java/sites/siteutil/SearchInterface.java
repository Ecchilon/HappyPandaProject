package sites.siteutil;

/**
 * Created by Ecchilon on 1/4/14.
 */
public abstract class SearchInterface extends PagedGalleryAbstract {

    String mQuery;

    public void setQuery(String query) {
        mQuery = query;
    }

    public String getQuery() {
        return  mQuery;
    }
}
