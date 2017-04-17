package com.example.android.newsfeedapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {

    // Log tag of the class
    private final String LOG_TAG = this.getClass().getSimpleName();

    // ID of the loader
    private static final int NEWS_LOADER_ID = 1;

    // HTTP Search string
    private String httpSearchString = "http://content.guardianapis.com/search?q=culture&api-key=test";

    // List view
    ListView mNewsListView;

    // Empty state view
    TextView mEmptyStateView;

    // Progress bar
    ProgressBar mProgressbar;

    // Adapter for the news items
    NewsAdapter mNewsAdapter;

    // Loader Manager
    LoaderManager mLoaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find empty state text view
        mEmptyStateView = (TextView) findViewById(R.id.empty_state_text_view);

        // Find list view and register empty state view to it
        mNewsListView = (ListView) findViewById(R.id.list_view);
        mNewsListView.setEmptyView(mEmptyStateView);

        // Find progress bar
        mProgressbar = (ProgressBar) findViewById(R.id.progress_bar);

        // Find the News Adapter and assign it to list view
        mNewsAdapter = new NewsAdapter(this, new ArrayList<News>());
        mNewsListView.setAdapter(mNewsAdapter);

        // Get loader manager
        mLoaderManager = this.getLoaderManager();

        // If internet state is connected or connecting, initiate the loader
        if (getInternetConnectivity()) {
            mLoaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            mProgressbar.setVisibility(View.INVISIBLE);
            mEmptyStateView.setText(R.string.no_internet_connection);
        }

        // Register on click listener to list view
        mNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Get current news item
                News currentNews = mNewsAdapter.getItem(position);

                // The URI to the news item on Internet
                Uri newsUri = Uri.parse(currentNews.getWebUrl());

                // Create and start intent
                Intent openOnInternet = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(openOnInternet);
            }
        });

    }

    /**
     * Helper method for checking internet connectivity
     *
     * @return true if network is in connected state or in connecting state
     */
    private boolean getInternetConnectivity() {

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    /**
     * Creates a new loader
     *
     * @param id   of the loader
     * @param args contains the arguments for the loader creation
     * @return a loader object
     */
    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {

        // return new NewsLoader object
        return new NewsLoader(this, httpSearchString);
    }

    /**
     * Actions taken when the loader process has finished and the list of
     * news items is provided
     *
     * @param loader   is the loader used during the load process
     * @param newsList is the list of News objects
     */
    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsList) {

        // Make progress bar invisible
        mProgressbar.setVisibility(View.INVISIBLE);
        // Set text for the empty state text view
        mEmptyStateView.setText(R.string.empty_state_string);

        // Clear the adapter
        mNewsAdapter.clear();

        // Fill adapter if News items are available
        if ((newsList != null) && (!newsList.isEmpty())) {
            mNewsAdapter.addAll(newsList);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Clear the adapter
        mNewsAdapter.clear();
    }
}
