package com.example.project2_v3.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2_v3.database.entities.MileM8;

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
}
