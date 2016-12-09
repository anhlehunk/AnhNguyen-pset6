package com.example.anh.anhnguyen_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void searchMessage (View view){
        Intent startNewActivity = new Intent(this, SearchActivity.class);
        startActivity(startNewActivity);
    }

    public void favoriteMessage (View view){
        Intent startNewActivity = new Intent(this, FavoriteActivity.class);
        startActivity(startNewActivity);
    }
}
