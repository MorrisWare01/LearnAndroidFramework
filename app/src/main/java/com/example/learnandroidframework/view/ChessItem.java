package com.example.learnandroidframework.view;

import android.graphics.Rect;

/**
 * Created by mmw on 2020/9/22.
 */
public class ChessItem {

    public Rect position;

    public float maxTranslateY = 0;
    public float translateY = 0;

    public ChessItem(Rect position) {
        this.position = position;
    }

    public Rect getPosition() {
        return position;
    }

}
