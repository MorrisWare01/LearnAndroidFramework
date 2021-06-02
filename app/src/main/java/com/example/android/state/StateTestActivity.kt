package com.example.android.state

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.android.SplashActivity


/**
 * Created by mmw on 2021/5/19.
 */
class StateTestActivity : AppCompatActivity() {

    companion object {
        var isFirst = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAG", "onCreate: ${savedInstanceState != null} ${toString()}")
        if (isFirst) {
            isFirst = false
            StateTest1Activity.callback = {
                Log.d("TAG", "callback: ${savedInstanceState != null} ${toString()}")
                if (!isDestroyed) {
                    startActivity(Intent(this, SplashActivity::class.java))
                }
            }
            startActivity(Intent(this, StateTest1Activity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
    }
}