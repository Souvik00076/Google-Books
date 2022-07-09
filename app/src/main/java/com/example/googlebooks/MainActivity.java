package com.example.googlebooks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText search;
    private Button searchBtn;
    //Sub Part of the link.These will Remain same for every type of search
    private final String LINK = "https://www.googleapis.com/books/v1/volumes?q=";
    private Spinner spinner;
    private String[] values;
    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initviews is used to initialized all the objects
        initViews();
        searchBtn.setOnClickListener(view -> {
            String flag = "\"" + search.getText().toString() + "\"";
            if (search.getText().toString().length() == 0)
                //if edit text is empty then we cannot initiate search
                Toast.makeText(MainActivity.this, "Search Field Cannot be empty", Toast.LENGTH_SHORT).show();
            else {
                /**
                 *  after we have selected specific spinner item and search item to be searched in the
                 *  edit text we append them in flag2 string start a new intent with extra.(extra is the
                 *  link to query here)
                 */

                String flag2 = LINK + value + flag;
                Intent intent = new Intent(getApplicationContext(), BookList.class);
                intent.putExtra("value", flag2);
                startActivity(intent);

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //based on the spinner item , specific string is selected .
                value = values[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void initViews() {
        search = findViewById(R.id.search_txt);
        searchBtn = findViewById(R.id.search_btn);
        spinner = findViewById(R.id.spinner_id);
        values = new String[]{"intitle:", "inauthor:", "inpublisher:", "subject:"};
        List<String> filters = new ArrayList<>();
        String[] flag = getResources().getStringArray(R.array.spinner_items);
        Collections.addAll(filters, flag);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, filters);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}