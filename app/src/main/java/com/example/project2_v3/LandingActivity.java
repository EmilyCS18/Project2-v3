package com.example.project2_v3;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        // Initialize buttons
        eventsButton = findViewById(R.id.button_LP_events);
        reportsButton = findViewById(R.id.button_LP_reports);
        vehicleButton = findViewById(R.id.button_LP_vehicles);
        adminButton = findViewById(R.id.button_LP_admin);
        logoutButton = findViewById(R.id.button_LP_logout);

        // Set onClickListeners for each button
        eventsButton.setOnClickListener(v -> startActivity(new Intent(LandingActivity.this, EventActivity.class)));
        reportsButton.setOnClickListener(v -> startActivity(new Intent(LandingActivity.this, ReportsActivity.class)));
        vehicleButton.setOnClickListener(v -> startActivity(new Intent(LandingActivity.this, VehicleActivity.class)));
        adminButton.setOnClickListener(v -> startActivity(new Intent(LandingActivity.this, AdminActivity.class)));
        logoutButton.setOnClickListener(v -> logoutUser());

        // Update visibility of admin button based on user permissions
        updateAdminButtonVis();
    }

    private void logoutUser() {
        Executors.newSingleThreadExecutor().execute(() -> {
            // Get the database instance
            MileM8Database db = MileM8Database.getDatabase(getApplicationContext());

            // Clear session-specific data only
            db.sessionTokenDAO().clearSessionTokens();

            // Run on the UI thread to update UI components
            runOnUiThread(() -> {
                // Redirect to LoginActivity
                Intent intent = new Intent(LandingActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Clear the activity stack
                startActivity(intent);
                finish();  // Ensure this activity is finished so user can't return with back button
            });
        });
    }


    private void updateAdminButtonVis() {
        // This function should update the visibility of the Admin button based on whether the user is an admin
        boolean isAdmin = false;  // Placeholder for actual admin check
        if (isAdmin) {
            adminButton.setVisibility(View.VISIBLE);
        } else {
            adminButton.setVisibility(View.INVISIBLE);
        }
    }
}
