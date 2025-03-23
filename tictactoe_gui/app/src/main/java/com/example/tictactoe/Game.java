package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.tictactoe.viewmodels.ScoresViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Game {

    private enum State {
        PLAY,
        PLAYER1WIN,
        PLAYER2WIN,
        COMPUTERWIN,
        CATS
    }

    private enum Turn {
        PLAYER1,
        PLAYER2,
        COMPUTER
    }

    Turn[] whoCanMakeATurn;
    Cell[][] cells;
    int rows = 3;
    int cols = 3;
    double cellWidth;
    double cellHeight;
    int screenWidth;
    int screenHeight;
    State state = State.PLAY;
    Turn turn = Turn.PLAYER1;
    String[][] positions;
    String[] xo = new String[]{"X", "O"};
    String gameMode;
    String player1Name;
    String player2Name;
    ScoresViewModel scoresViewModel;

    public Game(String gameMode, int screenWidth, int screenHeight, String player1Name, String player2Name, ScoresViewModel scoresViewModel) {
        this.scoresViewModel = scoresViewModel;
        this.gameMode = gameMode;
        this.player1Name = player1Name;
        cells = new Cell[rows][cols];
        positions = new String[rows][cols];
        if (this.gameMode.equals("AI")) {
            this.player2Name = "AI";
            whoCanMakeATurn = new Turn[]{Turn.PLAYER1, Turn.COMPUTER};
        } else {
            this.player2Name = player2Name;
            whoCanMakeATurn = new Turn[]{Turn.PLAYER1, Turn.PLAYER2};
        }
        cellHeight = (double)screenHeight / rows;
        cellWidth = (double)screenWidth / cols;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        initCells();
    }

    private List<Boolean[][]> getTests() {
        Boolean[][] test1 = {{true, false, false},
                            {true, false, false},
                            {true, false, false}};
        Boolean[][] test2 = {{false, true, false},
                            {false, true, false},
                            {false, true, false}};
        Boolean[][] test3 = {{false, false, true},
                            {false, false, true},
                            {false, false, true}};
        Boolean[][] test4 = {{true, true, true},
                            {false, false, false},
                            {false, false, false}};
        Boolean[][] test5 = {{false, false, false},
                            {true, true, true},
                            {false, false, false}};
        Boolean[][] test6 = {{false, false, false},
                            {false, false, false},
                            {true, true, true}};
        Boolean[][] test7 = {{true, false, false},
                            {false, true, false},
                            {false, false, true}};
        Boolean[][] test8 = {{false, false, true},
                            {false, true, false},
                            {true, false, false}};
        List<Boolean[][]> tests = new ArrayList<>();
        tests.add(test1);
        tests.add(test2);
        tests.add(test3);
        tests.add(test4);
        tests.add(test5);
        tests.add(test6);
        tests.add(test7);
        tests.add(test8);
        return tests;
    }

    private void initCells() {
        int xPos = 0;
        int yPos = 0;
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                cells[row][col] = new Cell(xPos, yPos, cellWidth, cellHeight, Cell.Type.EMPTY);
                positions[row][col] = " ";
                xPos += cellWidth;
            }
            xPos = 0;
            yPos += cellHeight;
        }
    }

    public void handleTap(MotionEvent e) {
        double xPos = e.getX();
        double yPos = e.getY();
        int[] point = getCellFromPoint(xPos, yPos);
        if (point != null) {
            int row = point[0];
            int col = point[1];
            int value = getValue(row, col);
            if (state == State.PLAY && !isPositionTaken(positions, value)) {
                if (whoCanMakeATurn[1] == Turn.COMPUTER) {
                    if (cells[row][col].getType() == Cell.Type.EMPTY) {
                        cells[row][col].setType(Cell.Type.X);
                        turn = Turn.COMPUTER;
                        updatePositions(row, col);
                        computerPlayerTurn(positions);
                        turn = Turn.PLAYER1;
                    }
                } else {
                    if (cells[row][col].getType() == Cell.Type.EMPTY) {
                        cells[row][col].setType(turn == Turn.PLAYER1 ? Cell.Type.X : Cell.Type.O);
                        updatePositions(row, col);
                        updateCells(row, col);
                    }
                    if (turn == Turn.PLAYER1) {
                        turn = Turn.PLAYER2;
                    } else {
                        turn = Turn.PLAYER1;
                    }
                }
            }
        }
        testWinner();
    }

    private void testWinner() {
        String winner = isWinner(getTests(), positions, xo);
        if (winner != null) {
            if (whoCanMakeATurn[1] == Turn.COMPUTER) {
                if (winner.equals("O")) {
                    state = State.COMPUTERWIN;
                } else {
                    state = State.PLAYER1WIN;
                }
            } else {
                if (winner.equals("O")) {
                    state = State.PLAYER2WIN;
                } else {
                    state = State.PLAYER1WIN;
                }
            }
        }
        if (isCats(positions)) {
            state = State.CATS;
        }
    }

    private void updatePositions(int row, int col) {
        if (cells[row][col].getType() == Cell.Type.EMPTY) {
            positions[row][col] = " ";
        } else if (cells[row][col].getType() == Cell.Type.X) {
            positions[row][col] = "X";
        } else {
            positions[row][col] = "O";
        }
    }

    private void updateCells(int row, int col) {
        if (Objects.equals(positions[row][col], " ")) {
            cells[row][col].setType(Cell.Type.EMPTY);
        } else if (Objects.equals(positions[row][col], "X")) {
            cells[row][col].setType(Cell.Type.X);
        } else if (Objects.equals(positions[row][col], "O")) {
            cells[row][col].setType(Cell.Type.O);
        }
    }

    private int getValue(int row, int col) {
        int value = 0;
        if (row == 0 && col == 0) {
            value = 1;
        } else if (row == 0 && col == 1) {
            value = 2;
        } else if (row == 0 && col == 2) {
            value = 3;
        } else if (row == 1 && col == 0) {
            value = 4;
        } else if (row == 1 && col == 1) {
            value = 5;
        } else if (row == 1 && col == 2) {
            value = 6;
        } else if (row == 2 && col == 0) {
            value = 7;
        } else if (row == 2 && col == 1) {
            value = 8;
        } else if (row == 2 && col == 2) {
            value = 9;
        }
        return value;
    }

    private void computerPlayerTurn(String[][] positions) {
        ComputerPlayer computerPlayer = new ComputerPlayer();
        Point point = computerPlayer.play(positions, "O");
        if (point != null) {
            positions[point.getR()][point.getC()] = "O";
            updateCells(point.getR(), point.getC());
        }
    }

    private static String isWinner(List<Boolean[][]> tests, String[][] positions, String[] xo) {
        String winner = null;
        // how to make a test for winner
        for (String v : xo) {
            for (Boolean[][] test : tests) {
                int score = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        String boardValueAtPosition = positions[i][j];
                        if (test[i][j] && boardValueAtPosition.equals(v)) {
                            score++;
                        }
                    }
                }
                if (score == 3) {
                    winner = v;
                }
            }
        }
        return winner;
    }

    private static boolean isPositionTaken(String[][] positions, int value) {
        int pos = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                pos++;
                if (value == pos && ("X".equals(positions[i][j]) || "O".equals(positions[i][j]))) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isCats(String[][] positions) {
        int score = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ("X".equals(positions[i][j]) || "O".equals(positions[i][j])) {
                    score++;
                }
            }
        }
        return score == 9;
    }

    public int[] getCellFromPoint(double xPos, double yPos) {
        for(int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                double cellX = col * cellWidth;
                double cellY = row * cellHeight;
                double cellXMax = cellX + cellWidth;
                double cellYMax = cellY + cellHeight;
                // this is the top left corner of the cell
                if (xPos >= cellX && xPos <= cellXMax && yPos >= cellY && yPos <= cellYMax) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    public void handleLongPress(MotionEvent e) {
    }

    public void draw(Canvas canvas, Paint paint) {
        for (Cell[] row : cells) {
            for (Cell col : row) {
                col.draw(canvas, paint);
                paint.reset();
            }
        }

        if (turn == Turn.PLAYER1) {
            paint.setColor(Color.BLACK);
            paint.setTextSize(50);
            canvas.drawText(player1Name + " Go (X)", 10, screenHeight - 20, paint);
            paint.reset();
        } else {
            paint.setColor(Color.BLACK);
            paint.setTextSize(50);
            canvas.drawText( player2Name+ " Go (O)", 10, screenHeight - 20, paint);
            paint.reset();
        }

        if (state == State.PLAYER1WIN) {
            Date date = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            scoresViewModel.saveScore(dateFormat.format(date), player1Name + " vs " + player2Name, player1Name);
            paint.setTextSize(100);
            paint.setColor(Color.GREEN);
            canvas.drawText(player1Name + " Wins!", screenWidth / 6, screenHeight / 2, paint);
            paint.reset();
        } else if (state == State.PLAYER2WIN) {
            Date date = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            scoresViewModel.saveScore(dateFormat.format(date), player1Name + " vs " + player2Name, player2Name);
            paint.setTextSize(100);
            paint.setColor(Color.GREEN);
            canvas.drawText(player2Name + " Wins!", screenWidth / 6, screenHeight / 2, paint);
            paint.reset();
        } else if (state == State.COMPUTERWIN) {
            Date date = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            scoresViewModel.saveScore(dateFormat.format(date), player1Name + " vs " + player2Name, player2Name);
            paint.setTextSize(200);
            paint.setColor(Color.MAGENTA);
            canvas.drawText("AI Wins!", screenWidth / 6, screenHeight / 2, paint);
            paint.reset();
        } else if (state == State.CATS) {
            Date date = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            scoresViewModel.saveScore(dateFormat.format(date), player1Name + " vs " + player2Name, "Cats");
            paint.setTextSize(200);
            paint.setColor(Color.MAGENTA);
            canvas.drawText("Cats", screenWidth / 6, screenHeight / 2, paint);
            paint.reset();
        }
    }

}
