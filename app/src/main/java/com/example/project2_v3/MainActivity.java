package com.example.project2_v3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.project2_v3.database.MileM8Repository;
import com.example.project2_v3.database.entities.User;
import com.example.project2_v3.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.project2_v3.MAIN_ACTIVITY_USER_ID";
    static final String SAVED_PREFERENCE_USERID_KEY = "com.example.project2_v3.SHARED_PREFERENCE_USERID_KEY";
    private static final int LOGGED_OUT = -1;
    private MileM8Repository repository;
    private int loggedInUserId = -1;
    private User user;
    ActivityMainBinding binding;
    public static final String TAG = "EJ_MILE_M8";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = MileM8Repository.getRepository(getApplication());
        loginUser(savedInstanceState);

        // Redirect to LandingActivity if user is logged in new method as of 4/20 @10PM
        if (loggedInUserId != -1) {
            Intent intent = new Intent(MainActivity.this, LandingActivity.class);
            startActivity(intent);
            finish(); // Close MainActivity
        }

        binding.buttonMainLogin.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));

        binding.buttonMainSignup.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SignupActivity.class)));
    }



    private void loginUser(Bundle savedInstanceState) {
        //check shared preference for logged in user
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.sharedprefrence_file_key),
                Context.MODE_PRIVATE);

            loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);

        if(loggedInUserId == LOGGED_OUT & savedInstanceState != null && savedInstanceState.containsKey(SAVED_PREFERENCE_USERID_KEY)){
            loggedInUserId = savedInstanceState.getInt(SAVED_PREFERENCE_USERID_KEY, LOGGED_OUT);
        }
        if(loggedInUserId == LOGGED_OUT){
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }
        if(loggedInUserId == LOGGED_OUT){
            return;
        }
        LiveData<User> userObserver = repository.getUserbyUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user = user;
            if(this.user != null){
                invalidateOptionsMenu();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@Nullable Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_PREFERENCE_USERID_KEY,loggedInUserId);
        updateSharedPreference();
    }

    static Intent mainActivityIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
    private void logout() {
        loggedInUserId = LOGGED_OUT;
        updateSharedPreference();
        getIntent().putExtra(MAIN_ACTIVITY_USER_ID,loggedInUserId);

        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    private void updateSharedPreference(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.sharedprefrence_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key), loggedInUserId);
        sharedPrefEditor.apply();

    }


}