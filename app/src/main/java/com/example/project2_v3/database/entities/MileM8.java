package com.example.project2_v3.database.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2_v3.database.MileM8Database;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity (tableName = MileM8Database.MILE_M_8_TABLE)
public class MileM8 {
    @PrimaryKey(autoGenerate = true)

    private int id;
    private String locations;
    private double miles;
    private double odometer;
    private double tollFees;
    private double parkingFees;
    private LocalDateTime date;
    private int userId;

    public MileM8(String locations, double miles, double odometer, double tollFees, double parkingFees, int userId) {
        this.locations = locations;
        this.miles = miles;
        this.odometer = odometer;
        this.tollFees = tollFees;
        this.parkingFees = parkingFees;
        this.userId = userId;
        date = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MileM8 mileM8 = (MileM8) o;
        return id == mileM8.id && Double.compare(mileM8.miles, miles) == 0 && Double.compare(mileM8.odometer, odometer) == 0 && Double.compare(mileM8.tollFees, tollFees) == 0 && Double.compare(mileM8.parkingFees, parkingFees) == 0 && userId == mileM8.userId && Objects.equals(locations, mileM8.locations) && Objects.equals(date, mileM8.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, locations, miles, odometer, tollFees, parkingFees, date, userId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public double getMiles() {
        return miles;
    }

    public void setMiles(double miles) {
        this.miles = miles;
    }

    public double getOdometer() {
        return odometer;
    }

    public void setOdometer(double odometer) {
        this.odometer = odometer;
    }

    public double getTollFees() {
        return tollFees;
    }

    public void setTollFees(double tollFees) {
        this.tollFees = tollFees;
    }

    public double getParkingFees() {
        return parkingFees;
    }

    public void setParkingFees(double parkingFees) {
        this.parkingFees = parkingFees;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
