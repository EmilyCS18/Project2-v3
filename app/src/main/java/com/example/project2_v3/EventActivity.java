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

import java.util.ArrayList;
import java.util.Calendar;

public class EventActivity extends AppCompatActivity {

    private Button dateButton;
    private Button timeButton;
    private EditText startingLocationEditText, destinationLocationEditText;
    private Button saveTripButton;

    private TextView selectVehicleTextView;
    private ArrayList<String> vehicleList = new ArrayList<>();
    private ArrayAdapter<String> vehicleAdapter;
    private ListView listView;


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

        //Vehicle stuff
        selectVehicleTextView = findViewById(R.id.select_Vehicle_Text);
        selectVehicleTextView.setOnClickListener(v -> showVehiclePopup());

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

     //vehicles stuff
     private void showVehiclePopup() {
         AlertDialog.Builder builder = new AlertDialog.Builder(this);
         LayoutInflater inflater = this.getLayoutInflater();
         View dialogView = inflater.inflate(R.layout.popup_select_vehicle, null);
         builder.setView(dialogView);

         listView = dialogView.findViewById(R.id.list_vehicles);
         EditText newVehicleName = dialogView.findViewById(R.id.new_vehicle_name);
         Button addVehicleButton = dialogView.findViewById(R.id.add_vehicle_button);

         // Initialize and set the adapter for the ListView
         vehicleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, vehicleList);
         listView.setAdapter(vehicleAdapter);

         // Load existing vehicles
         loadVehicles();

         // Add new vehicle button logic
         addVehicleButton.setOnClickListener(v -> {
             String vehicle = newVehicleName.getText().toString();
             if (!vehicle.isEmpty()) {
                 vehicleList.add(vehicle);
                 vehicleAdapter.notifyDataSetChanged();
                 newVehicleName.setText(""); // Clear the input field
             }
         });

         AlertDialog dialog = builder.create();
         dialog.show();
     }

    private void loadVehicles() {
        // Dummy data, replace this with actual data loading logic
        vehicleList.add("Car");
        vehicleList.add("Truck");
        vehicleList.add("Motorcycle");
        vehicleAdapter.notifyDataSetChanged();
    }
}