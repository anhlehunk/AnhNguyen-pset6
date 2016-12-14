package com.example.anh.anhnguyen_pset6;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Anh on 12-12-2016.
 */

public class SearchSingleArt extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... urls) {
        String id = urls[0];

        try {
            URL url = new URL("https://www.rijksmuseum.nl/api/nl/collection/" + id + "?key=KTtoPFMp&format=json");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }
}

