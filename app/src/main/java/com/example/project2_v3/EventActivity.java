package com.example.project2_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2_v3.database.entities.MileM8;
import com.example.project2_v3.database.MileM8Repository;

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
    private int userId;  // User ID to keep track of the logged-in user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // Retrieve user ID from intent
        userId = getIntent().getIntExtra("USER_ID", -1); // Default to -1 if not found

        dateButton = findViewById(R.id.datePickerButton);
        timeButton = findViewById(R.id.timePickerButton);
        startingLocationEditText = findViewById(R.id.starting_location);
        destinationLocationEditText = findViewById(R.id.destination_location);
        originalOdometerEditText = findViewById(R.id.original_odometer_type);
        newOdometerEditText = findViewById(R.id.new_odometer_type);
        mileageDifferenceTextView = findViewById(R.id.mileageDifferenceTextView);
        saveTripButton = findViewById(R.id.save_trip_button);

        dateButton.setOnClickListener(v -> showDatePickerDialog());
        timeButton.setOnClickListener(v -> showTimePickerDialog());
        saveTripButton.setOnClickListener(v -> saveLocationData());

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventActivity.this, LandingActivity.class);
            startActivity(intent);
            finish();
        });

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
        updateMileage();

        String startingLocation = startingLocationEditText.getText().toString();
        String destinationLocation = destinationLocationEditText.getText().toString();
        float tollFee = Float.parseFloat(((EditText) findViewById(R.id.toll_fee_type)).getText().toString());
        float parkingFee = Float.parseFloat(((EditText) findViewById(R.id.parking_fee_type)).getText().toString());
        double newOdometer = Double.parseDouble(newOdometerEditText.getText().toString());

        float totalExpenses = tollFee + parkingFee;

        MileM8 trip = new MileM8(startingLocation + " to " + destinationLocation, mileageDifference, newOdometer, tollFee, parkingFee, userId);

        MileM8Repository.getRepository(getApplication()).insertMileM8(trip);

        Toast.makeText(this, "Trip data saved!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(EventActivity.this, LandingActivity.class);
        startActivity(intent);
        finish();
    }
}
