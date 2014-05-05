package com.ecchilon.happypandaproject.sites;

/**
 * Created by Ecchilon on 1/4/14.
 */
public abstract class SearchAbstract extends StringContentAbstract {

    private String mQuery;

    public SearchAbstract(String query){
        mQuery = query;
    }

    public String getQuery() {
        return  mQuery;
    }
}
