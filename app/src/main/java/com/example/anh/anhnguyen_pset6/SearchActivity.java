package com.example.anh.anhnguyen_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    EditText user_searchInput;
    String user_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        user_searchInput = (EditText) findViewById(R.id.searchArt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu, menu);
        return true;
    }

    public void findArt(View view){

        user_input = user_searchInput.getText().toString();

        if (user_input.equals("")) {
            Toast.makeText(this, "Please enter an artwork or artist!", Toast.LENGTH_SHORT).show();}

        else{
            // moves to BooksFoundActivity, adds user input string
            Intent foundArt = new Intent(this,SearchFoundActivity.class);
            foundArt.putExtra("found_art", user_input);
            startActivity(foundArt);

            // clear the EditText
            user_searchInput.getText().clear();

        }

    }
}
