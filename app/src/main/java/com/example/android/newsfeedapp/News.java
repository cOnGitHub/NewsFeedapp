package com.example.android.newsfeedapp;

/**
 * Created by Christi on 17.04.2017.
 * A class representing a news item of the web site of The Guardian.
 */

public class News {

    // Section name to which the news item belongs, e.g. Politics, Business, etc.
    private String mSectionName;

    // Publication date of the news item on the web of the Guardian
    private String mPublicationDate;

    // Title of the news item
    private String mTitle;

    // Web URL of the news item
    private String mWebUrl;

    //
    public News(String sectionName, String publicationDate, String title, String webURL) {
        mSectionName = sectionName;
        mPublicationDate = publicationDate;
        mTitle = title;
        mWebUrl = webURL;
    }

    // Get the section name of the news item
    public String getSectionName() {
        return mSectionName;
    }

    // Get the publication date of the news item
    public String getPublicationDate() {
        return mPublicationDate;
    }

    // Get title of the news item
    public String getTitle() {
        return mTitle;
    }

    // Get the web URL of the news item
    public String getWebUrl() {
        return mWebUrl;
    }

    @Override
    public String toString() {
        return mPublicationDate + ": " + mTitle;
    }
}
