package com.example.booklist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String searchText;
    private static String BOOK_REQUEST_URL;
    private BookAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton searchButton = findViewById(R.id.search_button);
        EditText editText = findViewById(R.id.search_editText);
        TextView titleText = findViewById(R.id.appTitle);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchText = editText.getText().toString();
                titleText.setText(searchText);
                BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=" + searchText + "&maxResults=10";

                BookAsyncTask task = new BookAsyncTask();
                task.execute(BOOK_REQUEST_URL);
            }
        });

        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        ListView listView = findViewById(R.id.list);
        listView.setAdapter(mAdapter);
    }

    private class BookAsyncTask extends AsyncTask<String, Void, List<Book>> {

        @Override
        protected List<Book> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            List<Book> result = QueryUtil.fetchBooks(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Book> data) {
            mAdapter.clear();

            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }
}