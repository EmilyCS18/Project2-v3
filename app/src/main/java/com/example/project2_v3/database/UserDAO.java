package com.example.project2_v3.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project2_v3.database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);
    
    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + MileM8Database.USER_TABLE + " ORDER BY username")
    LiveData<List<User>> getALLUsers();

    @Query("DELETE from " + MileM8Database.USER_TABLE)
    void deleteAll();
    @Query("SELECT * from " + MileM8Database.USER_TABLE + " WHERE username == :username" )
    LiveData<User> getUserByUserName(String username);

    @Query("SELECT * from " + MileM8Database.USER_TABLE + " WHERE id == :userId" )
    LiveData<User> getUserByUserId(int userId);

    @Update
    void update(User user);
}
