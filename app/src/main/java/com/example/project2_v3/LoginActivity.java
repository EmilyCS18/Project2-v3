package com.example.project2_v3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    //TODO: Add login information
    int loggedinuserId = -1;

    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView signupRedirectText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        loginButton = findViewById(R.id.login_button);

        // addPredefinedUsers();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUsername() | !validatePassword()) {

                } else {
                    checkUser();
                }
            }
        });
        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    public Boolean validateUsername() {
        String val = loginUsername.getText().toString();
        if (val.isEmpty()) {
            loginUsername.setError("Username can't be empty");
            return false;
        } else {
            loginUsername.setError(null);
            return true;
        }
    }

    public Boolean validatePassword() {
        String val = loginPassword.getText().toString();
        if (val.isEmpty()) {
            loginPassword.setError("Password can't be empty");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }

    public void checkUser() {
        String userUsername = loginUsername.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        //Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        // checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
         /*   @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    loginUsername.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);
                    boolean isAdmin = snapshot.child(userUsername).child("isAdmin").getValue(Boolean.class);

                    if (Objects.equals(passwordFromDB, userPassword)) {
                        loginUsername.setError(null);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class); //is this working?
                        startActivity(intent);
                    } else {
                        loginPassword.setError("Invalid Credentials");
                        loginPassword.requestFocus();
                    }
                } else {
                    loginUsername.setError("User does not exist");
                    loginUsername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void addPredefinedUsers() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        usersRef.child("testuser1").setValue(new User("testuser1", "testuser1", false));
        usersRef.child("admin2").setValue(new User("admin2", "admin2", true));
    }

    public static class User {
        public String username;
        public String password;
        public boolean isAdmin;

        public User() {
            this.username = "defaultUser";
            this.password = "defaultPassword";
            this.isAdmin = false;

             }

        public User(String username, String password, boolean isAdmin) {
            this.username = username;
            this.password = password;
            this.isAdmin = isAdmin;
        }
    } */
    }
}