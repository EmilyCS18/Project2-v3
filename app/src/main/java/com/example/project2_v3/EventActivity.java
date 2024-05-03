package com.example.project2_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class EventActivity extends AppCompatActivity {

    private Button dateButton;
    private Button timeButton;
    private EditText startingLocationEditText, destinationLocationEditText;
    private Button saveTripButton;
    private EditText originalOdometerEditText, newOdometerEditText;
    private TextView mileageDifferenceTextView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        dateButton = findViewById(R.id.datePickerButton);
        timeButton = findViewById(R.id.timePickerButton);

        dateButton.setOnClickListener(v -> showDatePickerDialog());
        timeButton.setOnClickListener(v -> showTimePickerDialog());

        startingLocationEditText = findViewById(R.id.starting_location);
        destinationLocationEditText = findViewById(R.id.destination_location);
        saveTripButton = findViewById(R.id.save_trip_button);

        saveTripButton.setOnClickListener(v -> saveLocationData());

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventActivity.this, LandingActivity.class);
                startActivity(intent);
                finish(); // should it be finish or not?
            }
        });

        originalOdometerEditText = findViewById(R.id.original_odometer_type);
        newOdometerEditText = findViewById(R.id.new_odometer_type);
        mileageDifferenceTextView = findViewById(R.id.mileageDifferenceTextView);




    }
    private void showDatePickerDialog() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, yearSelected, monthOfYear, dayOfMonth) -> {
                    String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + yearSelected;
                    dateButton.setText(date);
                }, year, month, day);
        datePickerDialog.show();
}

    private void showTimePickerDialog() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minuteOfHour) -> {
                    String time = hourOfDay + ":" + minuteOfHour;
                    timeButton.setText(time);
                }, hour, minute, true);
        timePickerDialog.show();
    }

    private void saveLocationData() {
        int originalOdometer = Integer.parseInt(originalOdometerEditText.getText().toString());
        int newOdometer = Integer.parseInt(newOdometerEditText.getText().toString());

        // Calculate the mileage
        int mileageDifference = newOdometer - originalOdometer;

        // Display the mileage difference using TextView
        mileageDifferenceTextView.setText(mileageDifference + " miles");

        // Save the mileage difference
        SharedPreferences sharedPreferences = getSharedPreferences("MileageData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("lastMileageDifference", mileageDifference);
        editor.apply();
    }


}