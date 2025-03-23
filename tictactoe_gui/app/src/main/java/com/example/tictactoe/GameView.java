package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.tictactoe.models.Score;
import com.example.tictactoe.viewmodels.ScoresViewModel;

@SuppressLint("ViewConstructor")
public class GameView extends View {
    Game game;
    Paint paint = new Paint();
    String gameMode;
    String player1Name;
    String player2Name;
    @SuppressLint("ClickableViewAccessibility")
    public GameView(Context context, String gameMode, String player1Name, String player2Name) {
        super(context);
        this.gameMode = gameMode;
        this.player1Name = player1Name;
        this.player2Name = player2Name;

        GestureDetectorCompat detectorCompat = new GestureDetectorCompat(
                context,
                new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onDown(MotionEvent e) {
                        invalidate();
                        return true;
                    }

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        game.handleTap(e);
                        invalidate();
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        game.handleLongPress(e);
                        invalidate();
                    }
                });

        setOnTouchListener((view, e) -> {
            return detectorCompat.onTouchEvent(e);
        });
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        ScoresViewModel scoresViewModel = new ViewModelProvider((ViewModelStoreOwner) getContext()).get(ScoresViewModel.class);
        game = new Game(gameMode, getWidth(), getHeight(), player1Name, player2Name, scoresViewModel);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        game.draw(canvas, paint);
    }
}
