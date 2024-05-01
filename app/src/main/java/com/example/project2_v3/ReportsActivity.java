package com.example.project2_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReportsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        //from EventsActivity
        SharedPreferences sharedPreferences = getSharedPreferences("TripData", MODE_PRIVATE);
        String startingLocation = sharedPreferences.getString("StartingLocation", "Default Starting Location");
        String destinationLocation = sharedPreferences.getString("DestinationLocation", "Default Destination Location");


        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportsActivity.this, LandingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}