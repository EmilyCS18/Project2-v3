package com.example.project2_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.project2_v3.database.MileM8Database;

import java.util.concurrent.Executors;

public class LandingActivity extends AppCompatActivity {
    private Button eventsButton;
    private Button reportsButton;
    private Button vehicleButton;
    private Button adminButton;
    private Button logoutButton;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        // Retrieve the user ID from the intent
        userId = getIntent().getIntExtra("USER_ID", -1);

        // Initialize buttons
        eventsButton = findViewById(R.id.button_LP_events);
        reportsButton = findViewById(R.id.button_LP_reports);
        vehicleButton = findViewById(R.id.button_LP_vehicles);
        adminButton = findViewById(R.id.button_LP_admin);
        logoutButton = findViewById(R.id.button_LP_logout);

        // Set onClickListeners for each button and pass the user ID
        eventsButton.setOnClickListener(v -> {
            Intent eventIntent = new Intent(LandingActivity.this, EventActivity.class);
            eventIntent.putExtra("USER_ID", userId);
            startActivity(eventIntent);
        });
        reportsButton.setOnClickListener(v -> {
            Intent reportIntent = new Intent(LandingActivity.this, ReportsActivity.class);
            reportIntent.putExtra("USER_ID", userId);
            startActivity(reportIntent);
        });
        vehicleButton.setOnClickListener(v -> {
            Intent vehicleIntent = new Intent(LandingActivity.this, VehicleActivity.class);
            vehicleIntent.putExtra("USER_ID", userId);
            startActivity(vehicleIntent);
        });
        adminButton.setOnClickListener(v -> {
            Intent adminIntent = new Intent(LandingActivity.this, AdminActivity.class);
            adminIntent.putExtra("USER_ID", userId);
            startActivity(adminIntent);
        });
        logoutButton.setOnClickListener(v -> logoutUser());

        updateAdminButtonVis();
    }

    private void logoutUser() {
        Executors.newSingleThreadExecutor().execute(() -> {
            MileM8Database db = MileM8Database.getDatabase(getApplicationContext());

            clearUserIdFromSharedPreferences();

            // Run on the UI thread to update UI components
            runOnUiThread(() -> {
                Intent intent = new Intent(LandingActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Clear the activity stack
                startActivity(intent);
                finish();  // Ensure this activity is finished so user can't return with back button
            });
        });
    }

    private void updateAdminButtonVis() {
        // This function should update the visibility of the Admin button based on whether the user is an admin
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.sharedprefrence_file_key), Context.MODE_PRIVATE);
        boolean isAdmin = sharedPreferences.getBoolean(getString(R.string.preference_isAdmin_key),false);
        if (isAdmin) {
            adminButton.setVisibility(View.VISIBLE);
        } else {
            adminButton.setVisibility(View.INVISIBLE);
        }
    }

    private void clearUserIdFromSharedPreferences() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.sharedprefrence_file_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.preference_userId_key), -1);
        editor.apply();
    }
}