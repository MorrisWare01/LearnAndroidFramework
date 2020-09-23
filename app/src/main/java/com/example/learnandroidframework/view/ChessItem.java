package com.example.learnandroidframework.view;

import android.graphics.Rect;

/**
 * Created by mmw on 2020/9/22.
 */
public class ChessItem {

    public Rect position;

    public ChessItem next;

    public ChessItem(Rect position) {
        this.position = position;
    }

    public Rect getPosition() {
        return position;
    }

}
