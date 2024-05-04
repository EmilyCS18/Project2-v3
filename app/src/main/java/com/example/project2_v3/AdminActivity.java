package com.example.project2_v3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project2_v3.database.MileM8Repository;
import com.example.project2_v3.database.entities.User;

public class AdminActivity extends AppCompatActivity {
    EditText usernameText, passwordText;
    MileM8Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        usernameText = findViewById(R.id.admin_username_edittext);
        passwordText = findViewById(R.id.admin_password_edittext);

        repository = MileM8Repository.getRepository(getApplication());

        Button updatePasswordButton = findViewById(R.id.update_password_button);
        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();

                if (username.isEmpty()) {
                    Toast.makeText(AdminActivity.this,"Please enter a username.", Toast.LENGTH_LONG).show();
                }
                else if (password.isEmpty()) {
                    Toast.makeText(AdminActivity.this, "Please enter a password.", Toast.LENGTH_LONG).show();
                }
                else {
                    repository.getUserbyUserName(username).observe(AdminActivity.this, new Observer<User>() {
                        @Override
                        public void onChanged(User user) {
                            if (user != null) {
                                user.setPassword(password);
                                repository.updateUser(user);
                                Toast.makeText(AdminActivity.this, "Password updated for " + username, Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(AdminActivity.this, "Username not found.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}