package com.example.project2_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

public class EventActivity extends AppCompatActivity {

    private Button dateButton;
    private Button timeButton;
    private EditText startingLocationEditText, destinationLocationEditText;
    private Button saveTripButton;
    private EditText originalOdometerEditText, newOdometerEditText;
    private TextView mileageDifferenceTextView;
    private int mileageDifference;  // Mileage difference as a class member
    private String selectedDate = "";  // Date selected from DatePicker
    private String selectedTime = "";  // Time selected from TimePicker

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
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventActivity.this, LandingActivity.class);
            startActivity(intent);
            finish();
        });

        originalOdometerEditText = findViewById(R.id.original_odometer_type);
        newOdometerEditText = findViewById(R.id.new_odometer_type);
        mileageDifferenceTextView = findViewById(R.id.mileageDifferenceTextView);

        Button updateMilesButton = findViewById(R.id.update_miles_Button);
        updateMilesButton.setOnClickListener(v -> updateMileage());
    }

    private void showDatePickerDialog() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, yearSelected, monthOfYear, dayOfMonth) -> {
                    selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + yearSelected;
                    dateButton.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minuteOfHour) -> {
                    selectedTime = hourOfDay + ":" + minuteOfHour;
                    timeButton.setText(selectedTime);
                }, hour, minute, true);
        timePickerDialog.show();
    }

    private void updateMileage() {
        int originalOdometer = Integer.parseInt(originalOdometerEditText.getText().toString());
        int newOdometer = Integer.parseInt(newOdometerEditText.getText().toString());
        mileageDifference = newOdometer - originalOdometer;
        mileageDifferenceTextView.setText(mileageDifference + " miles");
    }

    private void saveLocationData() {
        // Update the mileage first
        updateMileage();

        // Capture starting and destination locations, and date and time
        String startingLocation = startingLocationEditText.getText().toString();
        String destinationLocation = destinationLocationEditText.getText().toString();

        // toll and parking fee inputs
        float tollFee = Float.parseFloat(((EditText) findViewById(R.id.toll_fee_type)).getText().toString());
        float parkingFee = Float.parseFloat(((EditText) findViewById(R.id.parking_fee_type)).getText().toString());

        // Calculate total expenses
        float totalExpenses = tollFee + parkingFee;

        // Save all data
        SharedPreferences sharedPreferences = getSharedPreferences("TripData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("lastMileageDifference", mileageDifference);
        editor.putFloat("TotalExpenses", totalExpenses);
        editor.putString("StartingLocation", startingLocation);
        editor.putString("DestinationLocation", destinationLocation);
        editor.putString("SelectedDate", selectedDate); // Save the date
        editor.putString("SelectedTime", selectedTime); // Save the time
        editor.apply();

        Toast.makeText(this, "Trip data saved!", Toast.LENGTH_SHORT).show();
    }
}
