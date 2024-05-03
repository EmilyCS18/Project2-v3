package com.example.project2_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LandingActivity extends AppCompatActivity {
    private Button eventsButton;
    private Button reportsButton;
    private Button vehicleButton;
    private Button adminButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        eventsButton = findViewById(R.id.button_LP_events);

        // Set OnClickListener for the Events Button
        eventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, EventActivity.class);
                startActivity(intent);
    }
        });

        reportsButton = findViewById(R.id.button_LP_reports);
        reportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, ReportsActivity.class);
                startActivity(intent);
            }
        });

        vehicleButton = findViewById(R.id.button_LP_vehicles);
        vehicleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, VehicleActivity.class);
                startActivity(intent);
            }
        });

        adminButton = findViewById(R.id.button_LP_admin);
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false);

        if (isAdmin) {
            adminButton.setVisibility(View.VISIBLE);
        }
        else {
            adminButton.setVisibility(View.GONE);
        }
    }

}