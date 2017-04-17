package com.example.android.newsfeedapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Adapter class for the news items
 * Created by Christi on 17.04.2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    // Log tag of this class
    private static final String LOG_TAG = NewsAdapter.class.getSimpleName();

    // The list of news
    private ArrayList<News> mNewsList;

    public NewsAdapter (Context context, List<News> newsList) {
        super(context, 0, newsList);
        mNewsList = (ArrayList<News>) newsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Get current News item
        News currentNews = mNewsList.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
        }

        // Find section name text view and set the section name
        TextView sectionNameTextView = (TextView) convertView.findViewById(R.id.section_name);
        sectionNameTextView.setText(currentNews.getSectionName());

        // Get the date time from the News item
        String dateTimeString =  currentNews.getPublicationDate();
        String dateTimeFormatted = "No date";

        // Try to change format of the data time string to "MMM d, yyyy"
        try {
            dateTimeFormatted = formatDateTime(dateTimeString);
        }
        catch (ParseException e)
        {
            Log.e(LOG_TAG, "Error on parsing date", e);
        }

        // Find publication date text view and set the date
        TextView publicationDateTextView = (TextView) convertView.findViewById(R.id.publication_date);
        publicationDateTextView.setText(dateTimeFormatted);

        // Find title text view and set the title
        TextView titleTextView = (TextView) convertView.findViewById(R.id.title_of_news_item);
        titleTextView.setText(currentNews.getTitle());

        return convertView;
    }

    /**
     * Helper method for creating a date format MMM d, yyyy from a DateTime object
     *
     * @param dateTimeString of a date and a time
     * @return a string representation of the date in format MMM d, yyyy
     */
    private String formatDateTime(String dateTimeString) throws ParseException {

        // Date format used by The Guardian APIs
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss'Z'");

        // Create a date object in the format used by The Guardian
        Date date = dateFormat.parse(dateTimeString);

        // Change the format of the SimpleDateFormat to "MMM d, yyyy"
        dateFormat.applyPattern("MMM d, yyyy");

        // Create a date time string with the new format
        String newDateTimeString = dateFormat.format(date);

        // Return the new date time string
        return newDateTimeString;

    }
}
