package com.example.project2_v3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.project2_v3.database.MileM8Repository;
import com.example.project2_v3.database.entities.MileM8;

import java.util.List;

public class ReportsActivity extends AppCompatActivity {
    private TextView totalTripsTextView, totalMilesTextView, totalExpensesTextView;
    private MileM8Repository  repository;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        userId = getUserIdFromSharedPreferences();

        totalTripsTextView = findViewById(R.id.total_trips_textview);
        totalMilesTextView = findViewById(R.id.total_miles_textview);
        totalExpensesTextView = findViewById(R.id.total_expenses_textview);

        repository = MileM8Repository.getRepository(getApplication());

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ReportsActivity.this, LandingActivity.class);
            startActivity(intent);
            finish();
        });

        repository.getAllMilesLiveData(userId).observe(this, new Observer<List<MileM8>>() {
            @Override
            public void onChanged(List<MileM8> trips) {
                updateUI(trips);
            }
        });
    }

    private void updateUI(List<MileM8> trips) {
        if (trips != null && !trips.isEmpty()) {
            int totalTrips = trips.size();
            double totalMiles = 0;
            double totalExpenses = 0;
            for (MileM8 trip : trips) {
                totalMiles += trip.getMiles();
                totalExpenses += trip.getTollFees() + trip.getParkingFees();
            }

            totalTripsTextView.setText("Total Trips: " + totalTrips);
            totalMilesTextView.setText("Total Miles: " + String.format("%.0f miles", totalMiles));
            totalExpensesTextView.setText(String.format("Total Expenses: $%.2f", totalExpenses));
        } else {
            totalTripsTextView.setText(R.string.total_trips_0);
            totalMilesTextView.setText(R.string.total_miles_0_miles);
            totalExpensesTextView.setText(R.string.total_expenses_0_00);
        }
    }

    private int getUserIdFromSharedPreferences() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.sharedprefrence_file_key), Context.MODE_PRIVATE);
        return sharedPreferences.getInt(getString(R.string.preference_userId_key), -1);
    }
}
