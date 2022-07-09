package com.example.googlebooks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


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


public class Utility {
    public static List<Book> HTTPSetupRequest(final String LINK) {
        System.out.println(LINK);
        String json = null;
        URL url = createURL(LINK);//URL object created
        try {
            if (url != null) {
                //incase of valid URL object , connection establishment phase is started
                json = setUpConnection(url);
            } else
                return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (json == null || json.isEmpty())
            return null;
        //in case of valid JSON scrap data is called which is used to fetch nedded data from the JSON data
        return scrapData(json);
    }

    public static void loadImage(List<Book> list) throws
            IOException {
        URL url;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        for (int i = 0; i < list.size(); i++) {
            try {
                String flag = list.get(i).getBitmap();
                if (flag == null) {
                    list.get(i).setBitmapID(null);
                    continue;
                }
                url = createURL(flag);
                if (url != null) {
                    urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    if (urlConnection.getResponseCode() == 200) {
                        inputStream = urlConnection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        list.get(i).setBitmapID(bitmap);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
                if (inputStream != null)
                    inputStream.close();
            }

        }
    }

    private static URL createURL(final String LINK) {
        try {
            return new URL(LINK);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String setUpConnection(final URL url) throws IOException {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            //connection establishment phase
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                //in case of successfull connection input stream is obtained and readInputStream method is called to read the raw data
                inputStream = urlConnection.getInputStream();
                return readInputStream(inputStream);//returns string
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (inputStream != null)
                inputStream.close();
        }
        return null;
    }

    private static String readInputStream(InputStream inputStream) throws IOException {
        //raw data is read here
        StringBuilder builder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line = br.readLine();
        while (line != null) {
            builder.append(line).append("\n");
            line = br.readLine();
        }
        br.close();
        return builder.toString();
    }

    private static List<Book> scrapData(final String json) {
        //Data Scraping Phase.This method returns List of Books
        List<Book> lists = new ArrayList<>();
        try {
            JSONObject rootObject;
            rootObject = new JSONObject(json);
            JSONArray array1 = rootObject.optJSONArray("items");
            if (array1 != null && array1.length() > 0) {
                for (int i = 0; i < array1.length(); i++) {
                    System.out.println("HERE CAME");
                    JSONObject object = array1.optJSONObject(i).optJSONObject("volumeInfo");
                    assert object != null;
                    String title = object.optString("title");
                    JSONArray author;
                    if (object.has("authors"))
                        author = object.getJSONArray("authors");
                    else
                        continue;
                    ArrayList<String> authors = new ArrayList<>();
                    for (int j = 0; j < author.length(); j++)
                        authors.add(author.optString(j));
                    String imageLink;
                    if (!object.has("imageLinks"))
                        imageLink = null;
                    else
                        imageLink = object.optJSONObject("imageLinks").optString("thumbnail");
                    String pub = object.optString("publisher");
                    String date = object.optString("publishedDate");
                    String description = object.optString("description");
                    String bookLink = object.optString("canonicalVolumeLink");
                    lists.add(new Book(imageLink, title, date, pub, authors, description, bookLink));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lists;
    }
}
