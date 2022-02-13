package com.example.booklist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Book currentBook = getItem(position);

        TextView titleTextView = listItemView.findViewById(R.id.book_title_textView);
        String title = currentBook.getTitle();
        titleTextView.setText(title);

        TextView authorTextView = listItemView.findViewById(R.id.author_TextView);
        String author = currentBook.getAuthor();
        authorTextView.setText(author);

        TextView isbnTextView = listItemView.findViewById(R.id.date_textView);
        String date = currentBook.getDate();
        isbnTextView.setText(date);

        return listItemView;
    }
}
