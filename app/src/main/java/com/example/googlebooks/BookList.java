package com.example.googlebooks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.List;

/**
 * This class implements the LoaderManager.LoaderCallBacks interface which is used to
 * initiate the query.
 */
public class BookList extends FragmentActivity implements
        LoaderManager.LoaderCallbacks<List<Book>> {
    private ProgressBar pBar;
    private ListView listView;
    private List<Book> list;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        initViews();
        /**
         *
         *initLoader calls onCreateLoader  method which is defined below onCreate
         *Ensures a loader is initialized and active. If the loader doesn't already exist,
         * one is created and (if the activity/fragment is currently started) starts the loader.
         * Otherwise the last created loader is re-used.
         * The first Parameter of initLoader is used to create a Loader Object which is an integer
         * It can be any value when there is only one Loader.
         * Second Parameter is optional
         * Third Parameter is the callback. It is an interface LoaderManager will call to report about changes in the state of the loader. Required.
         */
        getLoaderManager().initLoader(0, null, this);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(), FullView.class);
            Book book = list.get(i);
            intent.putExtra("Book Title", book.getTitle());
            intent.putStringArrayListExtra("Book Authors", book.getAuthors());
            intent.putExtra("Book Publisher", book.getPublishers());
            intent.putExtra("Book Publication Date", book.getDate());
            intent.putExtra("Book Description", book.getDescription());
            intent.putExtra("Book Preview Link", book.getBookLink());
            startActivity(intent);
        });
    }

    @NonNull
    @Override
    public Loader<List<Book>> onCreateLoader(int id, @Nullable Bundle args) {
        /**
         *Returns a new New Loader Object.Parameter passed are context,extras of the previous
         * intents and a progress bar object which is set when data is being loaded
         */
        return new DataLoader(getApplicationContext(), getIntent().getExtras().getString("value"), pBar);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Book>> loader, List<Book> data) {
        /**
         * When onCreateLoader finishes onLoadFinished  is called with the Loader that onCreateLoader
         * returned and data contained in it i.e the list of Books
         * if data is valid then loadAdapter method is called
         */
        pBar.setVisibility(View.GONE);
        list = data;
        //we must ensure that no previous data are there
        if (adapter != null)
            adapter.clear();
        if (data == null || data.isEmpty()) {
            TextView noDataTv = findViewById(R.id.noDataTV);
            noDataTv.setVisibility(View.VISIBLE);
            listView.setEmptyView(noDataTv);
        } else
            loadAdapter(data);
        //Loader is destroyed
        getLoaderManager().destroyLoader(loader.getId());

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Book>> loader) {

    }

    private void initViews() {
        listView = findViewById(R.id.bookList);
        pBar = findViewById(R.id.p_bar);
    }

    private void loadAdapter(List<Book> data) {
        //set ups the adapter
        adapter = new BookAdapter(this, data);
        listView.setAdapter(adapter);
    }
}