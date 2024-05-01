package com.example.project2_v3.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.project2_v3.database.entities.Vehicle;

@Database(entities = {Vehicle.class}, version = 1)
public abstract class VehicleDatabase extends RoomDatabase {
    public abstract VehicleDAO vehicleDao();

    private static volatile VehicleDatabase INSTANCE;

    static VehicleDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (VehicleDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    VehicleDatabase.class, "app_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
