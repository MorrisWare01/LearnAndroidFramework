package com.example.learnandroidframework.view

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.learnandroidframework.R

/**
 * @author mmw
 * @date 2020/5/8
 **/
class ViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
//        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
//                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER
//                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT
        }

        window.statusBarColor = Color.BLUE
        window.decorView.systemUiVisibility = (window.decorView.systemUiVisibility
//                or View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
//                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                )


        // test
        window.decorView.elevation = 50f

        setContentView(R.layout.activity_view)

//        val scrollView = findViewById<ScrollView>(R.id.scroll_view)
//        scrollView.viewTreeObserver.addOnGlobalLayoutListener(object :
//            ViewTreeObserver.OnGlobalLayoutListener {
//            override fun onGlobalLayout() {
//                scrollView.viewTreeObserver.removeOnGlobalLayoutListener(this)
//                // 滑动到底部
//                scrollView.fullScroll(ScrollView.FOCUS_DOWN)
//            }
//        })

        val chessBoardView = findViewById<ChessBoardView>(R.id.chess_board)

        chessBoardView.setOnApplyWindowInsetsListener { v, insets ->
            Log.d("TAG", insets.toString())
            insets
        }

        findViewById<View>(R.id.btn_start).setOnClickListener {
            chessBoardView.gameGo()
        }
        findViewById<View>(R.id.btn_load_scene).setOnClickListener {
            chessBoardView.loadScene()
        }
        findViewById<View>(R.id.btn_load_cube_scene).setOnClickListener {
            chessBoardView.loadCubeScene()
        }

        findViewById<View>(R.id.btn_go).setOnClickListener {
            chessBoardView.goTest()
        }
        findViewById<View>(R.id.btn_position).setOnClickListener {
            val view = it
            val intArray = IntArray(2)
            if (Build.VERSION.SDK_INT >= 29) {
                view.getLocationInSurface(intArray)
            }
            view.getLocationInWindow(intArray)
            view.getLocationOnScreen(intArray)
        }
    }

}