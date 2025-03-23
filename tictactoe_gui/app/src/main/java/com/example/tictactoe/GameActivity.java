package com.example.tictactoe;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.tictactoe.viewmodels.ScoresViewModel;

public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String gameMode = getIntent().getStringExtra("gameMode");
        String player1Name = getIntent().getStringExtra("player1Name");
        String player2Name = getIntent().getStringExtra("player2Name");
        GameView gameView = new GameView(this, gameMode, player1Name, player2Name);
        setContentView(gameView);
    }
}
