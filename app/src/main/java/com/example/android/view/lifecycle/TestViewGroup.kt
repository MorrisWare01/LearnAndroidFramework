package com.example.android.view.lifecycle

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * Created by mmw on 2021/5/7.
 */
class TestViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(
    context, attrs, defStyleAttr, defStyleRes
) {

    init {
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d("TestViewGroup", "${tag}-onMeasure")
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.d("TestViewGroup", "${tag}-onLayout")
    }

    override fun onDraw(canvas: Canvas?) {
        Log.d("TestViewGroup", "${tag}-onDraw")
        super.onDraw(canvas)
    }

}