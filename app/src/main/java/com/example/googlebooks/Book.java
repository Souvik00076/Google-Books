package com.example.googlebooks;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private final String bitmap;
    private Bitmap bitmapId;
    private final String title;
    private final String date;
    private final String publishers;
    private final String description;
    private final String bookLink;
    private final ArrayList<String> authors;

    public Book(String bitmap, String title, String date, String publishers,
                ArrayList<String> authors, String description, String bookLink) {
        this.bitmap = bitmap;
        this.title = title;
        this.date = date;
        this.publishers = publishers;
        this.description = description;
        this.bookLink = bookLink;
        this.authors = authors;
    }

    public String getBookLink() {
        return bookLink;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setBitmapID(Bitmap bitmapId) {
        this.bitmapId = bitmapId;
    }

    public Bitmap getBitmapID() {
        return bitmapId;
    }

    public String getBitmap() {
        return bitmap;
    }


    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }


    public String getDate() {
        return date;
    }


    public String getPublishers() {
        return publishers;
    }


}
