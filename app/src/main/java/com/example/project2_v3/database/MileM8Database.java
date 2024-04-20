package com.example.project2_v3.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.project2_v3.database.entities.MileM8;
import com.example.project2_v3.database.typeConverters.LocalDateTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {MileM8.class}, version = 1, exportSchema = false)
public abstract class MileM8Database extends RoomDatabase {

    private static final String DATABASE_NAME = "MileM8_database";
    public static final String MILE_M_8_TABLE = "mileM8Table";

    private static volatile MileM8Database INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static MileM8Database getDatabase(final Context context){
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
            //Log.i(MainActivity.TAG, "DATABASE CREATED!! ") uncomment when tag in created
            // TODO: add databaseWriteExecutor.execute(() -> {...}
        }
    };

    public abstract MileM8DAO milem8DAO();
}
