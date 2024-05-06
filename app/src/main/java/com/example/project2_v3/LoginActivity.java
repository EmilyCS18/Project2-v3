package com.example.project2_v3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.project2_v3.database.MileM8Repository;
import com.example.project2_v3.database.entities.User;
import com.example.project2_v3.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private static final String LOGIN_ACTIVITY_USER_ID = "com.example.project2_v3.LOGIN_ACTIVITY_USER_ID";
    static final String SAVED_PREFERENCE_USERID_KEY = "com.example.project2_v3.SHARED_PREFERENCE_USERID_KEY";
    private static final int LOGGED_OUT = -1;
    private MileM8Repository repository;

    private int loggedInUserId = -1;
    private User user;
    private boolean isAdmin;
    ActivityLoginBinding binding;
    public static final String TAG = "EJ_MILE_M8";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = MileM8Repository.getRepository(getApplication());

        binding.signupRedirectTextTextView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        binding.loginButton.setOnClickListener(v ->{
            loginUser(savedInstanceState);
            verifyUser();
        });
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
            loggedInUserId = getIntent().getIntExtra(LOGIN_ACTIVITY_USER_ID, LOGGED_OUT);
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

    private void verifyUser() {
        String username = binding.loginUsername.getText().toString();
        if (username.isEmpty()) {
            toastMaker("Username should not be blank");
            return;
        }
        LiveData<User> userObserver = repository.getUserbyUserName(username);
        userObserver.observe(this, user -> {
            if (user != null) {
                String password = binding.loginPassword.getText().toString();
                if (password.equals(user.getPassword())) {
                    //save id to shared preference
                    loggedInUserId = user.getId();
                    updateSharedPreference();

                    isAdmin = user.isAdmin();
                    updateAdminStatusInSharedPreferences();

                    startActivity(new Intent(LoginActivity.this, LandingActivity.class).putExtra("USER_ID", user.getId()));
                    finish();  // Optional: Close LoginActivity after redirecting
                } else {
                    toastMaker("Invalid Password");
                    binding.loginPassword.setSelection(0);
                }
            } else {
                toastMaker(String.format("%s is not a valid username.", username));
                binding.loginUsername.setSelection(0);
            }
        });
    }
    private void updateSharedPreference(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.sharedprefrence_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key), loggedInUserId);
        sharedPrefEditor.apply();

    }

    private void updateAdminStatusInSharedPreferences() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.sharedprefrence_file_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getString(R.string.preference_isAdmin_key),isAdmin);
        editor.apply();
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}
