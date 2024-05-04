package com.example.project2_v3.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.project2_v3.MainActivity;
import com.example.project2_v3.database.entities.MileM8;
import com.example.project2_v3.database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MileM8Repository {
    private final MileM8DAO milem8DAO;
    private final UserDAO userDAO;
    private ArrayList<MileM8> allMiles;
    public static MileM8Repository repository;


    public MileM8Repository(Application application){
        MileM8Database db = MileM8Database.getDatabase(application);
        this.milem8DAO = db.milem8DAO();
        this.userDAO = db.userDAO();
        this.allMiles = (ArrayList<MileM8>) this.milem8DAO.getAllRecords();

    }

    public static MileM8Repository getRepository(Application application){
        if(repository != null){
            return repository;
        }
        Future<MileM8Repository> future = MileM8Database.databaseWriteExecutor.submit(
                new Callable<MileM8Repository>() {
                    @Override
                    public MileM8Repository call() throws Exception {
                        return new MileM8Repository(application);
                    }
                }
        );
        try{
            return future.get();
        } catch (InterruptedException | ExecutionException e){
            Log.i(MainActivity.TAG, "Problem getting MileM8Repoitory, thread error.");
        }
        return null;
    }

    public LiveData<List<MileM8>> getAllMilesLiveData() {
        return milem8DAO.getAllTrips();
    }


    /*public ArrayList<MileM8> getAllMiles() {
        Future<ArrayList<MileM8>> future = MileM8Database.databaseWriteExecutor.submit(
                new Callable<ArrayList<MileM8>>() {
                    @Override
                    public ArrayList<MileM8> call() throws Exception {
                        return (ArrayList<MileM8>) milem8DAO.getAllRecords();
                    }
                });
        try{
            return future.get();
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
           Log.i(MainActivity.TAG, "Problem when getting all MilesLogs in the repository");
        }
        return null;

    }*/

    public void insertMileM8(MileM8 mileM8){
        MileM8Database.databaseWriteExecutor.execute(()->
                {
                    milem8DAO.insert(mileM8);
                });
    }

    public void insertUser(User... user){
        MileM8Database.databaseWriteExecutor.execute(()->
        {
            userDAO.insert(user);
        });
    }

    public LiveData<User> getUserbyUserName(String username) {
        return userDAO.getUserByUserName(username);
    }

    public LiveData<User> getUserbyUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }

    public LiveData<List<MileM8>> getTripsForMonth(String yearMonth) {
        return milem8DAO.getTripsForMonth(yearMonth);
    }

    public LiveData<List<MileM8>> getTripsForYear(String year) {
        return milem8DAO.getTripsForYear(year);
    }

}
