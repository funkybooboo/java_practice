package com.example.tictactoe.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Score {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    public String date;

    @ColumnInfo
    public String players;

    @ColumnInfo
    public String winner;

    @ColumnInfo(name = "created_at")
    public long createdAt;


}
