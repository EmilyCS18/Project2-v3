package com.example.project2_v3.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2_v3.database.MileM8Database;

import java.util.Objects;

@Entity(tableName = MileM8Database.VEHICLE_TABLE)
public class Vehicle {
    @PrimaryKey(autoGenerate = true)

    private int id;
    private String name;
    private String type;

    public Vehicle(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return id == vehicle.id && Objects.equals(name, vehicle.name) && Objects.equals(type, vehicle.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type);
    }
}
