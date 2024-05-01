package com.example.project2_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LandingActivity extends AppCompatActivity {
    private Button eventsButton;
    private Button vehicleButton;

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

        vehicleButton = findViewById(R.id.button_LP_vehicles);
        vehicleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, VehicleActivity.class);
                startActivity(intent);
            }
        });

    }

}