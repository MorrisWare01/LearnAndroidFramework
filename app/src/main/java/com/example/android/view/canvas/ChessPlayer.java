package com.example.android.view.canvas;

/**
 * Created by mmw on 2020/9/22.
 */
public class ChessPlayer {

    public ChessItem chessItem;

    public boolean isRunning;

    public float scaleX = 1;
    public float scaleY = 1;
    public float translateX = 0;
    public float translateY = 0;

    public void setChessItem(ChessItem chessItem) {
        this.chessItem = chessItem;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public void setTranslateX(float translateX) {
        this.translateX = translateX;
    }

    public void setTranslateY(float translateY) {
        this.translateY = translateY;
    }
}
