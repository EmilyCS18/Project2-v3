package com.example.project2_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

public class ReportsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        //from EventsActivity
        SharedPreferences sharedPreferences = getSharedPreferences("TripData", MODE_PRIVATE);
        String startingLocation = sharedPreferences.getString("StartingLocation", "Default Starting Location");
        String destinationLocation = sharedPreferences.getString("DestinationLocation", "Default Destination Location");

    }
}