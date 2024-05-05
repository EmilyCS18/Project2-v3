package com.example.project2_v3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project2_v3.database.MileM8Repository;
import com.example.project2_v3.database.entities.Vehicle;

public class VehicleActivity extends AppCompatActivity {

    EditText carNameText, carTypeText;
    MileM8Repository repository;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        userId = getIntent().getIntExtra("USER_ID",-1);

        carNameText = findViewById(R.id.car_name_text);
        carTypeText = findViewById(R.id.year_make_model_text);
        repository = MileM8Repository.getRepository(getApplication());

        setVehicleText();

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VehicleActivity.this, LandingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = carNameText.getText().toString();
                String type =  carTypeText.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(VehicleActivity.this, "Please enter a name.", Toast.LENGTH_SHORT).show();
                }
                else if (type.isEmpty()) {
                    Toast.makeText(VehicleActivity.this, "Please enter a type.", Toast.LENGTH_SHORT).show();
                }
                else {
                    repository.updateVehicleByUserId(userId,name,type);
                }
                setVehicleText();
            }
        });
    }

    private void setVehicleText() {
        LiveData<Vehicle> vehicleLiveData = repository.getVehicleByUserId(userId);
        vehicleLiveData.observe(this, new Observer<Vehicle>() {
            @Override
            public void onChanged(Vehicle vehicle) {
                if (vehicle != null) {
                    carNameText.setText(vehicle.getName());
                    carTypeText.setText(vehicle.getType());
                }
                else {
                    carNameText.setText(R.string.my_car);
                    carTypeText.setText(R.string.car_type);
                }
            }
        });
    }
}