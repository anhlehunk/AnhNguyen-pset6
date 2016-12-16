package com.example.anh.anhnguyen_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import static com.example.anh.anhnguyen_pset6.R.layout.activity_search_found;

public class SearchActivity extends AppCompatActivity {


    String user_input;
    private EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchText = (EditText)findViewById(R.id.searchArtEdit);

        //user_searchInput = (EditText) findViewById(R.id.searchArtEdit);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater minflater = getMenuInflater();
        minflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void findArt(View view) throws InterruptedException, ExecutionException, JSONException {

        user_input = searchText.getText().toString();
        if (user_input.equals("")) {
            Toast.makeText(this, "Please enter artwork or artist!", Toast.LENGTH_SHORT)
                    .show();}

        else{
                Log.d("LOGG", searchText.getText().toString());
                Intent searchArt = new Intent(this, SearchFoundActivity.class);
                searchArt.putExtra("searched_art", user_input);
                startActivity(searchArt);
                searchText.setText("");

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
