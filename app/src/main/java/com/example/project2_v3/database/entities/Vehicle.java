package com.example.project2_v3.database.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "vehicles")
public class Vehicle {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nickname;
    public String model;
    public int odometer;
}
