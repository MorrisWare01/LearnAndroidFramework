package com.example.learnandroidframework.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * @author mmw
 * @date 2020/6/19
 **/
class MainView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        color = Color.BLACK
    }

    private val mMatrix = Matrix()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val wMode = MeasureSpec.getMode(widthMeasureSpec)
        val wSize = MeasureSpec.getSize(widthMeasureSpec)

        val hMode = MeasureSpec.getMode(heightMeasureSpec)
        val hSize = MeasureSpec.getSize(heightMeasureSpec)

        Log.d(
            "TAG",
            "onMeasure: ${MeasureSpec.toString(widthMeasureSpec)} | ${MeasureSpec.toString(
                heightMeasureSpec
            )}"
        )
        setMeasuredDimension(wSize, hSize)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d("TAG", "onDraw")
        canvas.drawColor(Color.parseColor("#f2f2f2"))

//        paint.color = Color.BLACK
//        canvas.save()
//        canvas.translate(10f, 10f)
//        canvas.scale(0.5f, 0.5f);
//        canvas.drawRect(0f, 0f, 100f, 100f, paint)
//        canvas.restore()
//
//        paint.color = Color.BLACK
//        canvas.save()
//        canvas.translate(10f, 10f)
//        canvas.scale(0.5f, 0.5f, 50f, 50f);
//        canvas.drawRect(0f, 0f, 100f, 100f, paint)
//        canvas.restore()

        paint.color = Color.BLACK
        mMatrix.reset()
        mMatrix.preTranslate(10f, 10f)
//        mMatrix.preScale(2f, 2f)
        mMatrix.preRotate(30f)
        canvas.save()
        canvas.setMatrix(mMatrix)
        canvas.drawRect(0f, 0f, 100f, 100f, paint)
        canvas.restore()

        paint.color = Color.RED
        mMatrix.reset()
        mMatrix.postTranslate(10f, 10f)
//        mMatrix.postScale(2f, 2f)
        mMatrix.postRotate(30f)
        canvas.save()
        canvas.setMatrix(mMatrix)
        canvas.drawRect(0f, 0f, 100f, 100f, paint)
        canvas.restore()

    }

}