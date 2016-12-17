package com.example.anh.anhnguyen_pset6;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


import static android.widget.Toast.makeText;

import static com.firebase.ui.auth.ui.AcquireEmailHelper.RC_SIGN_IN;


public class SearchFoundActivity extends SearchActivity {

    // Assign variables

    public ListView lv;
    public ArrayList<String> idlist;
    public ArrayList<String> artlist;
    public ArrayList<String> urllist;
    String searched_art;
    TextView lookedFor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_found);

       //Get extras from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            searched_art = extras.getString("searched_art");
        }
        //Set the title of the activity
        lookedFor = (TextView) findViewById(R.id.lookedFor);
        lookedFor.setText("Looked for: " + searched_art);
        lv = (ListView)findViewById(R.id.searchlist);
         artlist = new ArrayList<>();
         urllist = new ArrayList<>();
         idlist = new ArrayList<>();

        search();
        // Handles clicks items of the list
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // takes the ID and give it to an intent, because the id is required to search for one specific item
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView idView = (TextView) view.findViewById(R.id.artID);
                String art_id = idView.getText().toString();
                Intent intent = new Intent(SearchFoundActivity.this, ArtInfoActivity.class);
                intent.putExtra("Info", art_id);
                startActivity(intent);
                SearchFoundActivity.this.finish();
            }
        });

        // Checks for saved instance state
        if (savedInstanceState != null) {
            artlist = (ArrayList<String>) savedInstanceState.getSerializable("items");
            ArtAdapter arrayAdapter = new ArtAdapter(SearchFoundActivity.this, artlist, urllist, idlist);
            SearchFoundActivity.this.lv.setAdapter(arrayAdapter);
        }
    }

    // Sets the savedinstancestate when the orientation is changed
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("items", artlist);
    }

    // AsyncTask to handle queries to the omdbapi, whether it is a search or a specific movie
    public class Task extends AsyncTask<String, Object, String> {
        //Textview for if there is no result, but the textview is still invisible before the AsyncTask
        TextView emptySearch = (TextView) findViewById(R.id.emptysearch);
        protected void onPreExecute() {
            emptySearch.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            //set the param as q
            String param = "q";
            try {
                //the url is built with the input of the user
                InputStream input = new URL("https://www.rijksmuseum.nl/api/nl/collection?key=Xs2UQsih&ps=50&format=json&" + URLEncoder.encode(param, "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8")).openStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return param + result;
            }
            catch (IOException e) {
                Log.d("MainActivity", "Error" + e);
                return null;
            }
        }
        //After the results are returned:
        public void onPostExecute(String result) {
            //split the  param from the useful query
            String param = result.substring(0, 1);
            String query = result.substring(1);
            try {
                //pick out the needed data out of the query
                JSONObject jsonObject = new JSONObject(query);
                if (jsonObject.has("Error")) {
                    artlist.clear();
                    ArtAdapter arrayAdapter = new ArtAdapter(SearchFoundActivity.this, artlist, urllist, idlist);
                    SearchFoundActivity.this.lv.setAdapter(arrayAdapter);
                }
                else {
                    if (param.equals("q")) {

                        // Clear the array
                        artlist.clear();
                        try {
                            //loops through all the results and add the title, image link, and unique id to two different lists
                            JSONArray jsonArray = jsonObject.getJSONArray("artObjects");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jObj = jsonArray.getJSONObject(i);
                                String title = jObj.getString("title");
                                String id = jObj.getString("objectNumber");

                                //add required data to lists
                                artlist.add(title);
                                idlist.add(id);
                                // Send "" (empty string)to the list when there is no image available
                                if(jObj.get("webImage").equals(null)){
                                    String url = "";
                                    urllist.add(url);
                                }
                                //Sends url of the image to lists if there is one available
                                else {
                                    JSONObject image = jObj.getJSONObject("webImage");
                                    String url = image.get("url").toString();
                                    urllist.add( url);}

                            }
                            //no results will cause the textview with "no results" to show
                            if ((artlist.size() == 0)){
                                emptySearch.setVisibility(View.VISIBLE);
                                Toast succesful = makeText(SearchFoundActivity.this, "No results found, please try again" , Toast.LENGTH_SHORT);
                                succesful.show();
                            }
                        //set adapter on the listview
                        ArtAdapter arrayAdapter = new ArtAdapter(SearchFoundActivity.this, artlist, urllist, idlist);
                        SearchFoundActivity.this.lv.setAdapter(arrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else {}}
            } catch (JSONException e) {
                e.printStackTrace();
            }}}



    // method to execute the AsyncTask
    // Handles a search query and starts a task

    public void search() {
        String param = "q";
        try {
            new Task().execute(searched_art, param).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    //navigation bar
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

    public void toFavorite(View view) {
        Intent intent = new Intent(this, FavoriteActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void logOut(View view){

        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                        Log.d("AUTH", "USER LOGGED OUT");

                        startActivityForResult(AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setTheme(R.style.FirebaseLoginTheme)
                                .setProviders(
                                        AuthUI.EMAIL_PROVIDER,
                                        AuthUI.GOOGLE_PROVIDER)

                                .build(), RC_SIGN_IN);
                        //toast
                        Toast succesful = makeText(SearchFoundActivity.this, "Logged out" , Toast.LENGTH_SHORT);
                        succesful.show();
                    }



                });


    }}

















