package com.example.learnandroidframework.activity.main

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.example.learnandroidframework.R
import kotlinx.android.synthetic.main.activity_fullscreen.*

/**
 * Created by mmw on 2021/3/10.
 * View.SYSTEM_UI_FLAG_FULLSCREEN 控制statusBar
 * View.SYSTEM_UI_FLAG_HIDE_NAVIGATION 控制navigationBar
 * View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
 * View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION 将应用内容填充到NavigationBar下面
 * View.SYSTEM_UI_FLAG_IMMERSIVE 设置沉浸模式，用户可通过顶部下滑和底部上滑显示Bar，拦截动作不传递到应用中，状态栏会恢复（systemUiVisibility值变成0）
 * View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY 设置粘性沉浸模式，用户通过顶部下滑和底部上滑显示Bar，不拦截动作传递到应用中, 状态栏会自动隐藏
 * View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR 设置状态栏黑色字体
 * View.SYSTEM_UI_FLAG_LOW_PROFILE 官方说是调暗状态栏
 * View.SYSTEM_UI_FLAG_LAYOUT_STABLE 设置后保存应用大小不变，例如:状态栏隐藏后白色填充高度
 */
class FullscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // AppCompat设置没有标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            Toast.makeText(this, "visibility: $visibility", Toast.LENGTH_SHORT).show()
        }
        setContentView(R.layout.activity_fullscreen)

        btnFullscreen.setOnClickListener {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
        btnLeanback.setOnClickListener {
            // 设置后，点击屏幕任意位置就重新显示状态栏
            // 系统会直接拦截触摸事件
            window.decorView.systemUiVisibility =
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
        btnImmersive.setOnClickListener {
            // 设置后，顶部下拉/底部上滑重新显示状态栏
            // 系统会直接拦截触摸事件
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_IMMERSIVE or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
        btnStickyImmersive.setOnClickListener {
            // 设置后，顶部下拉/底部上滑重新显示状态栏
            // 轻触手势会传递给应用，因此应用也会响应该手势。
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
        btnLightStatusBar.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR != 0) {
                    window.decorView.systemUiVisibility =
                        window.decorView.systemUiVisibility.and(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv())
                } else {
                    window.decorView.systemUiVisibility =
                        window.decorView.systemUiVisibility.or(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                }
            }
        }
        btnLowProfiler.setOnClickListener {
            if (window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LOW_PROFILE != 0) {
                window.decorView.systemUiVisibility =
                    window.decorView.systemUiVisibility.and(View.SYSTEM_UI_FLAG_LOW_PROFILE.inv())
            } else {
                window.decorView.systemUiVisibility =
                    window.decorView.systemUiVisibility.or(View.SYSTEM_UI_FLAG_LOW_PROFILE)
            }
        }
        btnRotation.setOnClickListener {
            requestedOrientation =
                if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                } else {
                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                }
        }
        btnDrawStatusBar.setOnClickListener {
            window.decorView.systemUiVisibility = 0
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == ORIENTATION_PORTRAIT) {
            window.decorView.systemUiVisibility = 0
        } else {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }
    }

    override fun onBackPressed() {
        if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            return
        }
        super.onBackPressed()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.d(FullscreenActivity::class.simpleName, "dispatchTouchEvent:" + ev.toString())
        return super.dispatchTouchEvent(ev)
    }

}