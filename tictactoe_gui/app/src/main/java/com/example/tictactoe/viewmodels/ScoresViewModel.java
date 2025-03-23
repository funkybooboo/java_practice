package com.example.tictactoe.viewmodels;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.tictactoe.database.AppDatabase;
import com.example.tictactoe.models.Score;

import java.util.ArrayList;

public class ScoresViewModel extends AndroidViewModel {
    private final AppDatabase database;
    private final MutableLiveData<Boolean> saving = new MutableLiveData<>();
    private final ObservableArrayList<Score> entries = new ObservableArrayList<>();
    private final Handler handler;
    private final MutableLiveData<Score> currentEntry = new MutableLiveData<>();
    public ScoresViewModel(@NonNull Application application) {
        super(application);
        saving.setValue(false);
        handler = new Handler();
        database = Room.databaseBuilder(application, AppDatabase.class, "Scoresdb").build();
        new Thread(() -> {
            ArrayList<Score> scores = (ArrayList<Score>) database.getScoresDao().getAll();
            handler.post(() -> {
                entries.addAll(scores);
            });
        }).start();
    }

    public MutableLiveData<Score> getCurrentEntry() {
        return currentEntry;
    }

    public void setCurrentEntry(Score entry) {
        this.currentEntry.setValue(entry);
    }

    public MutableLiveData<Boolean> getEntry() {
        return saving;
    }

    public ObservableArrayList<Score> getEntries() {
        return entries;
    }

    public void saveScore(String date, String players, String winner) {
        saving.setValue(true);
        new Thread(() -> {
            if (currentEntry.getValue() != null) {
                Score current = currentEntry.getValue();
                current.date = date;
                current.players = players;
                current.winner = winner;
                database.getScoresDao().update(current);
                currentEntry.postValue(current);
                int index = entries.indexOf(current);
                entries.set(index, current);
            } else {
                Score newEntry = new Score();
                newEntry.date = date;
                newEntry.players = players;
                newEntry.winner = winner;
                newEntry.createdAt = System.currentTimeMillis();
                newEntry.id = database.getScoresDao().insert(newEntry);
                entries.add(newEntry);
            }
            saving.postValue(false);
        }).start();
    }

}
