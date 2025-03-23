package com.example.tictactoe.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tictactoe.models.Score;

import java.util.List;

@Dao
public interface ScoresDao {

    @Insert
    public long insert(Score entry);

    @Query("SELECT * FROM Score")
    public List<Score> getAll();

    @Query("SELECT * FROM Score WHERE id = :id LIMIT 1")
    public Score findById(long id);

    @Update
    public void update(Score entry);

}
