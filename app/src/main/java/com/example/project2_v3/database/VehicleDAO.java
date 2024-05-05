package com.example.project2_v3.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("DELETE FROM " + MileM8Database.VEHICLE_TABLE)
    void deleteAllVehicles();


    @Delete
    void delete(Vehicle vehicle);

   @Update
    void update(Vehicle vehicle);

   @Query("UPDATE " + MileM8Database.VEHICLE_TABLE + " SET name = :name, type = :type WHERE id = :userId")
    void updateVehicleByUserId(int userId, String name, String type);
}
