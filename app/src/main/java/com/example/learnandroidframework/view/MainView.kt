package com.example.learnandroidframework.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.learnandroidframework.R

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
    private val mPath = Path()
    private val mPaint = Paint().apply {
//        style = Paint.Style.STROKE
        color = Color.RED
    }

    private val mBitmap = BitmapFactory.decodeResource(resources, R.mipmap.dice_build_left_1)
    private val mSrcRect = Rect(0, 0, mBitmap.width, mBitmap.height)
    private val mDstRect = Rect(500, 0, 500 + mBitmap.width, mBitmap.height)


    private val maxHeight: Int = (context.resources.displayMetrics.density * 200).toInt()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val wMode = MeasureSpec.getMode(widthMeasureSpec)
        val wSize = MeasureSpec.getSize(widthMeasureSpec)

        val hMode = MeasureSpec.getMode(heightMeasureSpec)
        val hSize = MeasureSpec.getSize(heightMeasureSpec)

        Log.d(
            "TAG",
            "onMeasure: ${MeasureSpec.toString(widthMeasureSpec)} | ${
                MeasureSpec.toString(
                    heightMeasureSpec
                )
            }"
        )
        setMeasuredDimension(wSize, maxHeight.coerceAtMost(hSize))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d("TAG", "onDraw")
        canvas.saveLayer(0f, 0f, width.toFloat(), 300f, mPaint)
        canvas.translate(50f, 50f)
        mPath.reset()
        mPath.fillType = Path.FillType.INVERSE_WINDING
        mPath.addOval(0f, 0f, 50f, 100f, Path.Direction.CW)
        mPath.addRect(150f, 150f, 200f, 200f, Path.Direction.CW)
        // simple type
        mPath.addRoundRect(
            300f, 100f, 400f, 200f, floatArrayOf(
                20f, 20f, 20f, 20f, 20f, 20f, 20f, 20f
            ), Path.Direction.CW
        )
        // rect
        mPath.addRoundRect(
            500f, 100f, 600f, 200f, floatArrayOf(
                20f, 0f, 20f, 0f, 0f, 20f, 0f, 20f
            ), Path.Direction.CW
        )
        // ninePatch
        mPath.addRoundRect(
            700f, 100f, 800f, 200f, floatArrayOf(
                10f, 20f, 30f, 40f, 30f, 40f, 10f, 20f
            ), Path.Direction.CW
        )
        // complex
        mPath.addRoundRect(
            900f, 100f, 1000f, 200f, floatArrayOf(
                10f, 20f, 30f, 40f, 50f, 60f, 70f, 80f
            ), Path.Direction.CW
        )
        // oval
        mPath.addRoundRect(
            1100f, 100f, 1200f, 200f, floatArrayOf(
                50f, 50f, 50f, 50f, 50f, 50f, 50f, 50f
            ), Path.Direction.CW
        )
        canvas.drawPath(mPath, mPaint)
        canvas.restore()

        canvas.save()
        canvas.translate(50f, 0f)
        canvas.scale(-1f, 1f, (mBitmap.width / 2 + 500).toFloat(), (mBitmap.height / 2).toFloat())
//        canvas.drawBitmap(mBitmap, 0f, 0f, null)
        canvas.drawBitmap(mBitmap, mSrcRect, mDstRect, null)
        canvas.restore()
    }

}