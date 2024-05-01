package com.example.project2_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class EventActivity extends AppCompatActivity {
    private Button dateButton;
    private Button timeButton;
    private EditText startingLocationEditText, destinationLocationEditText;
    private Button saveTripButton;

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
        String startingLocation = startingLocationEditText.getText().toString();
        String destinationLocation = destinationLocationEditText.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("TripData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("StartingLocation", startingLocation);
        editor.putString("DestinationLocation", destinationLocation);
        editor.apply(); // Use apply() instead of commit() for asynchronous data saving
    }
}