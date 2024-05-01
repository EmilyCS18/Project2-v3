package com.example.project2_v3;

public class Vehicle {
    private String name;
    private String make;
    private String model;
    private int year;
    private double mileage;
    private int vin;

    public Vehicle(String name, String make, String model, int year, double mileage, int vin) {
        this.name = name;
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.vin = vin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public int getVin() {
        return vin;
    }

    public void setVin(int vin) {
        this.vin = vin;
    }

    @Override
    public String toString() {
        return name + ": " + year + " " + make + " " + model + ", mileage: " + mileage + ", vin: " + vin;
    }
}
