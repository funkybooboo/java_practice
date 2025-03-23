package com.example.tictactoe;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Cell {
    public enum Type {
        X,
        O,
        EMPTY
    }

    private Type type;
    private final double xPos;
    private final double yPos;
    private final double width;
    private final double height;
    public Cell(double xPos, double yPos, double width, double height, Type type) {
        this.type = type;
        this.yPos = yPos;
        this.xPos = xPos;
        this.width = width;
        this.height = height;
    }

    public void draw(Canvas canvas, Paint paint) {
        double percentForX = 0.30;
        double percentForY = 0.70;
        Rect rect = new Rect();
        rect.set((int)xPos, (int)yPos, (int)xPos + (int)width, (int)yPos + (int)height);
        drawBoarderAroundCell(canvas, paint, rect);
        if (type.equals(Type.EMPTY)) {
            paint.setColor(Color.WHITE);
            canvas.drawRect(rect, paint);
            paint.reset();
        }
        if (type.equals(Type.O)) {
            paint.setColor(Color.RED);
            paint.setTextSize(200);
            canvas.drawText("O", (int)(xPos + (width*percentForX)), (int)(yPos + (height*percentForY)), paint);
            paint.reset();
        }
        if (type.equals(Type.X)) {
            paint.setColor(Color.BLUE);
            paint.setTextSize(200);
            canvas.drawText("X", (int)(xPos + (width*percentForX)), (int)(yPos + (height*percentForY)), paint);
            paint.reset();
        }
        drawBoarderAroundCell(canvas, paint, rect);
    }

    private void drawBoarderAroundCell(Canvas canvas, Paint paint, Rect rect) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        canvas.drawRect(rect, paint);
        paint.reset();
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
