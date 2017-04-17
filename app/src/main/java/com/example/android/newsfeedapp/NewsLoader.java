package com.example.android.newsfeedapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Class representing a loader for the News items.
 *
 * Created by Christi on 17.04.2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    // Log tag of the class
    private final String LOG_TAG = this.getClass().getSimpleName();

    // The URL string
    private String mUrlString;

    /**
     * Constructor of a NewsLoader object
     *
     * @param context is the context from which the load process is called
     * @param urlString is the URL which is used for the loading process
     */
    public NewsLoader(Context context,String urlString) {
        super(context);
        mUrlString = urlString;
    }

    /**
     * Force load when starting the loading process
     */
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * Loading process running in the background
     * @return a list of News items
     */
    @Override
    public List<News> loadInBackground() {
        return QueryUtils.getNewsData(mUrlString);
    }
}
