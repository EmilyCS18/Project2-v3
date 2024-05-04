package com.example.project2_v3.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.project2_v3.database.entities.SessionToken;

@Dao
public interface SessionTokenDAO {
    @Insert
    void insert(SessionToken sessionToken);

    @Query("DELETE FROM session_tokens")
    void clearSessionTokens();

    @Query("SELECT * FROM session_tokens WHERE token = :token")
    SessionToken getSessionTokenByToken(String token);

    @Delete
    void deleteSessionToken(SessionToken sessionToken);
}
