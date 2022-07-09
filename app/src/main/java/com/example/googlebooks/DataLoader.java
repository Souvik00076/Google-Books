package com.example.googlebooks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.AsyncTaskLoader;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.List;

public class DataLoader extends AsyncTaskLoader<List<Book>> {
    final String LINK;
    @SuppressLint("StaticFieldLeak")
    private final ProgressBar pBar;

    /**
     * @param context is passed from the BookList Activity
     * @param LINK    is the URL which is needed to query
     * @param pBar    is the ProgressBar object which is set on when connection setup
     *                and data loading phases continues
     */

    DataLoader(final Context context, final String LINK, ProgressBar pBar) {
        super(context);
        this.LINK = LINK;
        this.pBar = pBar;
    }

    @Override
    public List<Book> loadInBackground() {
        //HTTPSetupRequest call is used to setup connection & loading data
        List<Book> list =
                Utility.HTTPSetupRequest(LINK);
        if (list == null || list.isEmpty())
            return null;
        try {
            Utility.loadImage(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void onStartLoading() {
        pBar.setIndeterminate(true);
        pBar.setVisibility(View.VISIBLE);
        //without the force call loadInBackground method will not be called
        forceLoad();
    }

}
