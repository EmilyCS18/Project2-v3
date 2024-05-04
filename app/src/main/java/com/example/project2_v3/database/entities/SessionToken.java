package com.example.project2_v3.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "session_tokens")
public class SessionToken {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String token;
    private long timestamp;

    public SessionToken(String token, long timestamp) {
        this.token = token;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
