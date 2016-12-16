package com.example.anh.anhnguyen_pset6;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import static android.R.attr.start;
import static android.os.Build.VERSION_CODES.M;
import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private static int RC_SIGN_IN = 0;
    private static String TAG = "MAIN_ACTIVITY";
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;



    public static Bundle myBundle = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            //already logged in

            Log.d("AUTH", mAuth.getCurrentUser().getEmail());
            TextView currentUser = (TextView) findViewById(R.id.loginInfo);
            currentUser.setText(mAuth.getCurrentUser().getEmail());


        } else {

            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setTheme(R.style.FirebaseLoginTheme)
                    .setProviders(
                            AuthUI.EMAIL_PROVIDER,
                            AuthUI.GOOGLE_PROVIDER)

                    .build(), RC_SIGN_IN);
        }


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                    Log.d("Auth", "user logged in: " + user.getEmail());
                else
                    Log.d("Auth", "user logged out");
            }

        };
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        findViewById(R.id.SignOut).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null)
            mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if(resultCode == RESULT_OK){
                String userEmail = mAuth.getCurrentUser().getEmail();
                Log.d("AUTH", userEmail);

                // Toast
                Toast succesful = makeText(MainActivity.this, "Logged in as: " + userEmail , Toast.LENGTH_SHORT);
                succesful.show();
                TextView currentUser = (TextView) findViewById(R.id.loginInfo);
                currentUser.setText(mAuth.getCurrentUser().getEmail());

            }

            else{
                Log.d("AUTH","not Authenticated");
            }

            /*if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }

            else
                Log.d(TAG," google login failed"); */
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct){
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Auth", "signInWithCredential:oncomplete:" + task.isSuccessful());
                    }
                });
    }

    public void searchMessage (View view){
        Intent startNewActivity = new Intent(this, SearchActivity.class);
        startActivity(startNewActivity);
    }

    public void favoriteMessage (View view){
        Intent startNewActivity = new Intent(this, FavoriteActivity.class);
        startActivity(startNewActivity);
    }


    private void signOut(){

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
                        Toast succesful = makeText(MainActivity.this, "Logged out" , Toast.LENGTH_SHORT);
                        succesful.show();
                    }
                });


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.d(TAG, "Connection failed");

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.SignOut:
                signOut();
                break;
        }
    }
}
