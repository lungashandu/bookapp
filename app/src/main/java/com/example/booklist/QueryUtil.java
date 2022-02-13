package com.example.booklist;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class QueryUtil {
    public static final String LOG_TAG = QueryUtil.class.getSimpleName();

    private QueryUtil() {
    }

    public static List<Book> fetchBooks(String requestUrl) {
        URL url = createUrl(requestUrl);

        String JSONResponse = null;
        try {
            JSONResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream ", e);
        }
        return extractFeatureFromJSON(JSONResponse);
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String JSONResponse = "";

        if (url == null) {
            return JSONResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                JSONResponse = readFromStream(inputStream);
                Log.e(LOG_TAG, String.valueOf(urlConnection.getResponseCode()));
            } else {
                Log.e(LOG_TAG, "Error: response code " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the book JSON results. ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return JSONResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Book> extractFeatureFromJSON(String bookJSON) {
        List<Book> books = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(bookJSON);
            JSONArray itemsArray = root.getJSONArray("items");

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject bookObject = itemsArray.getJSONObject(i);
                JSONObject volumeInfoObject = bookObject.getJSONObject("volumeInfo");

                String title = volumeInfoObject.getString("title");
                JSONArray authorArray = volumeInfoObject.getJSONArray("authors");
                String author = authorArray.getString(0);
                String date = volumeInfoObject.getString("publishedDate");

                JSONObject accessInfoObject = bookObject.getJSONObject("accessInfo");
                String url = accessInfoObject.getString("webReaderLink");

                Book book = new Book(title, author, date, url);
                books.add(book);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the book JSON results " + e);
        }

        return books;
    }
}
