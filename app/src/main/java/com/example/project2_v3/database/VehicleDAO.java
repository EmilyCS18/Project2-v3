package com.example.project2_v3.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2_v3.database.entities.Vehicle;

import java.util.List;

@Dao
public interface VehicleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Vehicle vehicle);

    @Query("Select * from " + MileM8Database.VEHICLE_TABLE)
    List<Vehicle> getAllVehicles();

    @Query("SELECT * from " + MileM8Database.VEHICLE_TABLE + " WHERE id == :userId" )
    LiveData<Vehicle> getVehicleByUserId(int userId);

    @Delete
    void delete(Vehicle vehicle);
}
