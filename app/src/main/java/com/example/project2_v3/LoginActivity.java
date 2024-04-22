package com.example.project2_v3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

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
        setContentView(binding.getRoot());

        repository = MileM8Repository.getRepository(getApplication());

        binding.signupRedirectTextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        binding.loginButton.setOnClickListener(v -> verifyUser());
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

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}
