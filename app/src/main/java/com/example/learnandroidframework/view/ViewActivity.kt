package com.example.learnandroidframework.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowManager
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import com.example.learnandroidframework.R

/**
 * @author mmw
 * @date 2020/5/8
 **/
class ViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE

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
        findViewById<View>(R.id.btn_go).setOnClickListener {
            chessBoardView.goTest()
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
    }

}