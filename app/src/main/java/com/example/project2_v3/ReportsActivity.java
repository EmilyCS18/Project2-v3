package com.example.project2_v3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.project2_v3.database.MileM8Repository;
import com.example.project2_v3.database.entities.MileM8;

import java.util.List;

public class ReportsActivity extends AppCompatActivity {
    private MileM8Repository repository;
    private LiveData<List<MileM8>> trips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        // Initialize the repository and set up data observation
        repository = new MileM8Repository(getApplication());
        trips = repository.getTripsForMonth("2023-04"); // Example for April 2023

        trips.observe(this, new Observer<List<MileM8>>() {
            @Override
            public void onChanged(List<MileM8> mileM8s) {
                // Update your RecyclerView or other UI elements here
            }
        });

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
