package com.example.booklist;

public class Book {
    private final String mTitle;
    private final String mAuthor;
    private final String mDate;
    private final String mUrl;

    public Book(String title, String author, String date, String url) {
        mTitle = title;
        mAuthor = author;
        mDate = date;
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }


}
