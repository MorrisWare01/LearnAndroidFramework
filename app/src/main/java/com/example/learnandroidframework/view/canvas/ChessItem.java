package com.example.learnandroidframework.view.canvas;

import android.graphics.Rect;

/**
 * Created by mmw on 2020/9/22.
 */
public class ChessItem implements Comparable<ChessItem> {

    public int type;


    public Rect position;

    public float maxTranslateY = 0;
    public float translateY = 0;

    public float alpha = 1;
    public int zIndex = 0;

    public ChessItem(Rect position) {
        this.position = position;
    }

    public Rect getPosition() {
        return position;
    }

    @Override
    public int compareTo(ChessItem o) {
        return o.zIndex > zIndex ? -1 : 1;
    }

}
