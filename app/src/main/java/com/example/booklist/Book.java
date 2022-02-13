package com.example.booklist;

public class Book {
    private final String mTitle;
    private String mAuthor;
    private String mDate;

    public Book(String title, String author, String date) {
        mTitle = title;
        mAuthor = author;
        mDate = date;
    }

    public Book(String title) {
        mTitle = title;
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


}
