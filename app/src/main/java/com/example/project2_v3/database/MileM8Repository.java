package com.example.project2_v3.database;

import android.app.Application;

import com.example.project2_v3.database.entities.MileM8;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MileM8Repository {
    private MileM8DAO milem8DAO;
    private ArrayList<MileM8> allMiles;
    public MileM8Repository(Application application){
        MileM8Database db = MileM8Database.getDatabase(application);
        this.milem8DAO = db.milem8DAO();
        this.allMiles = (ArrayList<MileM8>) this.milem8DAO.getAllRecords();
    }

    public ArrayList<MileM8> getAllMiles() {
        Future<ArrayList<MileM8>> future = MileM8Database.databaseWriteExecutor.submit(
                new Callable<ArrayList<MileM8>>() {
                    @Override
                    public ArrayList<MileM8> call() throws Exception {
                        return (ArrayList<MileM8>) milem8DAO.getAllRecords();
                    }
                }
        );
        try{
            return future.get();
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
           //Log.i(MainActivity.TAG, "Problem when getting all MilesLogs in the repository");
        }
        return null;

    }

    public void insertMileM8(MileM8 mileM8){
        MileM8Database.databaseWriteExecutor.execute(()->
                {
                    milem8DAO.insert(mileM8);
                });
    }

}
