package com.example.project2_v3.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project2_v3.database.entities.Vehicle;

import java.util.List;

@Dao
public interface VehicleDAO {
    @Query("SELECT * FROM vehicles")
    List<Vehicle> getAllVehicles();

    @Insert
    void insertVehicle(Vehicle vehicle);

    @Update
    void updateVehicle(Vehicle vehicle);

    @Delete
    void deleteVehicle(Vehicle vehicle);
}
