package com.example.tictactoe.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.tictactoe.models.Score;;

@Database(entities = {Score.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ScoresDao getScoresDao();
}
