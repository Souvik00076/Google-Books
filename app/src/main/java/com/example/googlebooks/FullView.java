package com.example.googlebooks;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/*
* This class is used to show the user with full view of the content
* Some information like title,author names and conetent description of the book
* are shown here
 */
public class FullView extends AppCompatActivity {

    private TextView titleView, authorView, publisherView, dateView, descriptionview;
    private String preViewLink;
    private Button visitButton;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view);
        getSupportActionBar().hide();
        initViews();
        String title, date, publisher, description;
        ArrayList<String> authors = getIntent().getStringArrayListExtra("Book Authors");
        StringBuilder builder = new StringBuilder();
        int i;
        for (i = 0; i < authors.size() - 1; i++)
            builder.append(authors.get(i)).append(" , ");
        builder.append(authors.get(i));
        title = getIntent().getStringExtra("Book Title");
        publisher = getIntent().getStringExtra("Book Publisher");
        date = getIntent().getStringExtra("Book Publication Date");
        description = getIntent().getStringExtra("Book Description");
        preViewLink = getIntent().getStringExtra("Book Preview Link");
        titleView.setText(title);
        authorView.setText(builder.toString());
        publisherView.setText(publisher);
        dateView.setText(date);
        String NO_TEXT = "No Description To Show";
        if (description.isEmpty())
            descriptionview.setText(NO_TEXT);

        else
            descriptionview.setText(description);
        visitButton.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(preViewLink));
            startActivity(intent);
            finish();
        });
    }

    private void initViews() {
        titleView = findViewById(R.id.title_id);
        authorView = findViewById(R.id.author_id);
        publisherView = findViewById(R.id.pub_id);
        dateView = findViewById(R.id.date_id);
        descriptionview = findViewById(R.id.descp_id);
        descriptionview.setMovementMethod(new ScrollingMovementMethod());
        visitButton = findViewById(R.id.b_id);
    }
}