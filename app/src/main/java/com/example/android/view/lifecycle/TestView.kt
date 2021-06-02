package com.example.android.view.lifecycle

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * Created by mmw on 2021/5/7.
 */
class TestView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(
    context, attrs, defStyleAttr, defStyleRes
) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d("TestView", "${tag}-onMeasure")
    }

    override fun onDraw(canvas: Canvas?) {
        Log.d("TestView", "${tag}-onDraw")
        super.onDraw(canvas)
    }

}