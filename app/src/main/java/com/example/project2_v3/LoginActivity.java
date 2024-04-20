package com.example.project2_v3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2_v3.database.MileM8Repository;
import com.example.project2_v3.database.entities.User;
import com.example.project2_v3.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private MileM8Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        repository = MileM8Repository.getRepository(getApplication());

        binding.signupRedirectTextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!verifyUser()) {
                    Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();

                } else {//TODO: 14:40 on video 10 
                    //Intent intent = LandingPage.

                    startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                }
            }
        });
    }
    private boolean verifyUser(){
        String username = binding.loginUsername.getText().toString();
        if(username.isEmpty()){
            Toast.makeText(this, "Username May Not Be Blank.", Toast.LENGTH_SHORT).show();
            return false;
        }
        User user = repository.getUserbyUserName(username);
        if (user != null){
            String password = binding.loginPassword.getText().toString();
            if(password.equals(user.getPassword())){
                return true;
            }

        }
        return false;
    }


    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginActivity.class);
    }

    private void showLogoutDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(LoginActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Do You Want To Logout?");

        alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertBuilder.create().show();
    }

    private void logout() {
        //TODO: Finish logout method
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }
}





