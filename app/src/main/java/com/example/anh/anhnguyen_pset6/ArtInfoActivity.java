package com.example.anh.anhnguyen_pset6;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import static android.R.attr.id;
import static com.example.anh.anhnguyen_pset6.R.id.proberen;

public class ArtInfoActivity extends AppCompatActivity {

    String art_id;
    String title;
    String image_object_string;
    String art_ID;
    String description;
    JSONObject art_object;
    JSONObject image_object;
    TextView artTitle;
    TextView artDescription;
    TextView artMaker;
    TextView year;
    ImageView artImage;

    TextView resultText;
    String result;
    Button button;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public static final String myPreference = "mypreference";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.art_info);
        artTitle = (TextView) findViewById(R.id.title);
        artDescription = (TextView) findViewById(R.id.description);
        artMaker = (TextView) findViewById(R.id.maker);
        year = (TextView) findViewById(R.id.year);
        artImage = (ImageView) findViewById(R.id.artImage);

        // Finds preferences and assign an editor
        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        button = (Button)findViewById(R.id.addBut);

        search();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            art_id = extras.getString("Info");}

     }

    // AsyncTask to handle queries to the API
    public class Task_info extends AsyncTask<String, Object, String> {
        protected void onPreExecute() {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                art_id = extras.getString("Info");}


        }

        @Override
        protected String doInBackground(String... params) {

            // Retrieves the param and fills the URl accordingly and returns the results of the query
            //String param = art_id;


            try {
                InputStream input = new URL("https://www.rijksmuseum.nl/api/nl/collection/" + art_id + "?key=KTtoPFMp&format=json&culture=en").openStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null) {
                    result.append(line);
                }


                return result.toString();

            }
            catch (IOException e) {
                return null;
            }
        }

        public void onPostExecute(String result) {

        resultText = (TextView) findViewById(proberen);
        resultText.setText(result);
        resultText.setVisibility(View.GONE);
        order();
        }}

    public void order(){
        resultText = (TextView) findViewById(proberen);
        result = resultText.getText().toString();

        try {
            JSONObject total_object = new JSONObject(result);
            art_object = total_object.getJSONObject("artObject");
            String maker_text = art_object.getJSONArray("principalMakers").getJSONObject(0).get("name").toString();;
            title = art_object.get("title").toString() ;
            art_ID = art_object.get("objectNumber").toString() ;
            description = art_object.get("description").toString();


            artTitle.setText(title);
            artDescription.setText("Description:\n"  + description);
            artMaker.setText("Artist(s) :" + maker_text);
            String year_text = "Year: " + art_object.getJSONObject("dating").get("year").toString();
            year.setText(year_text);
            if (art_object.get("webImage").toString() == "null" ) {
                artImage.setImageResource(R.drawable.no_image);
            } else {
                // Picasso used to place the image retrieved from the url in the imageview.
                image_object = art_object.getJSONObject("webImage");
                image_object_string = art_object.getJSONObject("webImage").toString();
                Picasso.with(this).load(image_object.get("url").toString()).resize(1000, 1000).into(artImage);

            }
            if (sharedPreferences.contains(art_ID)) {
                button.setText("Remove");
            }
            else {
                button.setText("Add");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void addRemove(View view) {
        if (button.getText().equals("Add")) {
            editor.putString(art_ID,title);


            editor.commit();
            button.setText("Remove");
        }
        else {
            editor.remove(art_ID);

            editor.commit();
            button.setText("Add");
        }
    }



    public void search() {


        String param = "";
        try {
            new Task_info().execute(art_id, param).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void toHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void toSearchPage(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void toWatchList(View view) {
        Intent intent = new Intent(this, FavoriteActivity.class);
        startActivity(intent);
        this.finish();
    }



}
