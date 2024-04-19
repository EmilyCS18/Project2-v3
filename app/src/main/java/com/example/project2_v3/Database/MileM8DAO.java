package com.example.project2_v3.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2_v3.Database.entities.MileM8;

import java.util.ArrayList;

@Dao
public interface MileM8DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MileM8 mileM8);

    @Query("Select * from " + MileM8Database.MILE_M_8_TABLE)
    ArrayList<MileM8> getAllRecords();
}
