package com.example.anh.anhnguyen_pset6;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import static android.widget.Toast.makeText;
import static com.example.anh.anhnguyen_pset6.R.layout.activity_search_found;
import static com.firebase.ui.auth.ui.AcquireEmailHelper.RC_SIGN_IN;

public class SearchActivity extends AppCompatActivity {
    //variables
    String user_input;
    private EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //find edit text
        searchText = (EditText)findViewById(R.id.searchArtEdit);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater minflater = getMenuInflater();
        minflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void findArt(View view) throws InterruptedException, ExecutionException, JSONException {
        user_input = searchText.getText().toString();
        //make sure that the user searches for something
        if (user_input.equals("")) {
            Toast.makeText(this, "Please enter artwork or artist!", Toast.LENGTH_SHORT)
                    .show();}

        //If the user searched for something, an intent will be given to the next activity, where the
        //input is used to activate the AsyncTask
        else{
                Intent searchArt = new Intent(this, SearchFoundActivity.class);
                searchArt.putExtra("searched_art", user_input);
                startActivity(searchArt);
                searchText.setText("");
        }}
    // navigation bar at the bottom
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
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("AUTH", "USER LOGGED OUT");
                        startActivityForResult(AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setTheme(R.style.FirebaseLoginTheme)
                                .setProviders(
                                        AuthUI.EMAIL_PROVIDER,
                                        AuthUI.GOOGLE_PROVIDER)
                                .build(), RC_SIGN_IN);
                        //toast
                        Toast succesful = makeText(SearchActivity.this, "Logged out" , Toast.LENGTH_SHORT);
                        succesful.show();
                    }



                });
}}
