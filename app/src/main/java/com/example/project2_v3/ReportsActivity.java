package com.example.project2_v3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.project2_v3.database.MileM8Repository;
import com.example.project2_v3.database.entities.MileM8;

import java.util.List;

public class ReportsActivity extends AppCompatActivity {

    private TextView totalTripsTextView, totalMilesTextView, totalExpensesTextView;
    private MileM8Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        totalTripsTextView = findViewById(R.id.total_trips_textview);
        totalMilesTextView = findViewById(R.id.total_miles_textview);
        totalExpensesTextView = findViewById(R.id.total_expenses_textview);

        repository = MileM8Repository.getRepository(getApplication());

        LiveData<List<MileM8>> tripsLiveData = repository.getAllMilesLiveData();
        tripsLiveData.observe(this, new Observer<List<MileM8>>() {
            @Override
            public void onChanged(List<MileM8> trips) {
                updateUI(trips);
            }
        });

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ReportsActivity.this, LandingActivity.class);
            startActivity(intent);
            finish();
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
            totalTripsTextView.setText("Total Trips: 0");
            totalMilesTextView.setText("Total Miles: 0 miles");
            totalExpensesTextView.setText("Total Expenses: $0.00");
        }
    }
}
