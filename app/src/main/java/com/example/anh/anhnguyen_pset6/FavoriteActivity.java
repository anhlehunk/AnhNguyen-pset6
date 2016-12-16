package com.example.anh.anhnguyen_pset6;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static android.R.id.edit;
import static com.google.android.gms.internal.zznu.it;

public class FavoriteActivity extends AppCompatActivity {

    // Create variables
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public static final String myPreference = "mypreference";
    public static final String Name = "nameKey";
    public static final String title = "title";

    TextView welcomeText;
    ListView lv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        // Retrieve preferences and assign editor
        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();



        // Find ListView
        lv = (ListView)findViewById(R.id.watchList);

        // Loop through the added movies and put those movies inside an array
        final ArrayList<String> artlist = new ArrayList<String>();
        final ArrayList<String> idlist = new ArrayList<String>();
        ArrayList<String> urllist = new ArrayList<String>();
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            //Log.d("ccontain", String.valueOf(allEntries) );
            if (!entry.getKey().equals(Name)/* && entry.getKey().equals("ArtTitle")*/ ) {
                idlist.add(entry.getKey().toString());
                artlist.add(entry.getValue().toString());
                Log.d("ccontain", String.valueOf(idlist));
                Log.d("ccontain", String.valueOf(artlist));
            }

        }


        FavoriteAdapter arrayAdapter = new FavoriteAdapter(FavoriteActivity.this, artlist, idlist);
        FavoriteActivity.this.lv.setAdapter(arrayAdapter);

        // Use the created array to fill the listview through an adapter
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.proberen1, artlist);
        //this.lv.setAdapter(arrayAdapter);

        // Hanlde clicks on the listview
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // Retrieve the movie title and execute a Asynctask using that title
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView idView = (TextView) view.findViewById(R.id.artID);
                String art_id = idView.getText().toString();
                Intent intent = new Intent(FavoriteActivity.this, ArtInfoActivity.class);
                intent.putExtra("Info", art_id);
                startActivity(intent);
                FavoriteActivity.this.finish();
            }
        });

        // Hanlde clicks on the listview
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            FavoriteAdapter arrayAdapter = new FavoriteAdapter(FavoriteActivity.this, artlist, idlist);
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView idView = (TextView) view.findViewById(R.id.artID);
                TextView titleView = (TextView) view.findViewById(R.id.title);
                String deleted = "Deleted";
                titleView.setText(deleted);
                titleView.setTextColor(getResources().getColor(R.color.red));
                String art_id = idView.getText().toString();
                editor.remove(art_id);
                editor.commit();
                arrayAdapter.notifyDataSetChanged();
                return true;
            }


        });
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




