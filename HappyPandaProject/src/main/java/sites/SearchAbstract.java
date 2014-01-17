package sites;

/**
 * Created by Ecchilon on 1/4/14.
 */
public abstract class SearchAbstract extends StringContentAbstract {

    String mQuery;

    public SearchAbstract(String query){
        mQuery = query;
    }

    public String getQuery() {
        return  mQuery;
    }

    @Override
    public String getTitle() {
        return "\"" + mQuery + "\"";
    }
}
