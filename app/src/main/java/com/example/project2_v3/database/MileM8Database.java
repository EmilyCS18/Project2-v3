package com.example.project2_v3.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.project2_v3.MainActivity;
import com.example.project2_v3.database.entities.MileM8;
import com.example.project2_v3.database.entities.SessionToken;
import com.example.project2_v3.database.entities.User;
import com.example.project2_v3.database.entities.Vehicle;
import com.example.project2_v3.database.typeConverters.LocalDateTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {MileM8.class, User.class, Vehicle.class, SessionToken.class}, version = 2, exportSchema = false)
public abstract class MileM8Database extends RoomDatabase {

    public static final String USER_TABLE = "usertable";
    private static final String DATABASE_NAME = "MileM8database";
    public static final String MILE_M_8_TABLE = "mileM8Table";
    public static final String VEHICLE_TABLE = "vehicleTable";

    private static volatile MileM8Database INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;


    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MileM8Database getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (MileM8Database.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            MileM8Database.class,
                            DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final  RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED!! ");
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                VehicleDAO vdao = INSTANCE.vehicleDAO();
                dao.deleteAll();
                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                dao.insert(admin);
                Vehicle vehicle1 = new Vehicle("Admin Car", "Admin Type");
                vdao.insert(vehicle1);
                User testUser1 = new User ("testuser1", "testuser1");
                dao.insert(testUser1);
                Vehicle vehicle2 = new Vehicle("My Car","Car Type");
                vdao.insert(vehicle2);
            });
        }
    };

    public void clearNonUserData() {
        vehicleDAO().deleteAllVehicles();
        milem8DAO().deleteAllMileM8();
        // Add similar lines for other DAOs if needed
    }


    public abstract MileM8DAO milem8DAO();

    public abstract UserDAO userDAO();

    public abstract VehicleDAO vehicleDAO();

    public abstract SessionTokenDAO sessionTokenDAO();

}
