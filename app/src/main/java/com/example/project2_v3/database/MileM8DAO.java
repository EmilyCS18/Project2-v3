package com.example.project2_v3.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2_v3.database.entities.MileM8;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Dao
public interface MileM8DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MileM8 mileM8);

    @Query("Select * from " + MileM8Database.MILE_M_8_TABLE)
    List<MileM8> getAllRecords();

    @Query("Select * from " + MileM8Database.MILE_M_8_TABLE + " WHERE userId = :loggedInUserId ORDER BY date DESC")
    List<MileM8> getRecordsetUserId(int loggedInUserId);


    // Retrieve all trips
    @Query("SELECT * FROM milem8table")
    LiveData<List<MileM8>> getAllTrips();

    @Query("SELECT * FROM mileM8Table WHERE userId = :userId")
    LiveData<List<MileM8>> getAllTripsByUser(int userId);

    // Retrieve trips between two dates
    @Query("SELECT * FROM mileM8Table WHERE date BETWEEN :startDate AND :endDate")
    LiveData<List<MileM8>> getTripsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);

    // Retrieve trips for a specific month and year
    @Query("SELECT * FROM mileM8Table WHERE strftime('%Y-%m', date / 1000, 'unixepoch') = :yearMonth")
    LiveData<List<MileM8>> getTripsForMonth(String yearMonth);

    // Retrieve trips for a specific year
    @Query("SELECT * FROM mileM8Table WHERE strftime('%Y', date / 1000, 'unixepoch') = :year")
    LiveData<List<MileM8>> getTripsForYear(String year);

    @Query("DELETE FROM " + MileM8Database.MILE_M_8_TABLE)
    void deleteAllMileM8();
}
