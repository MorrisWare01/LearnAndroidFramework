package com.example.learnandroidframework.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.learnandroidframework.R
import kotlinx.android.synthetic.main.activity_service_test.*

/**
 * @author mmw
 * @date 2020/6/3
 **/
class ServiceTestActivity : AppCompatActivity() {

    private var isBound = false
    private var mainService: MainService.IMainService? = null
    private val conn = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("TAG", "onServiceDisconnected")
            isBound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d("TAG", "onServiceConnected")
            isBound = true
            mainService = service as MainService.IMainService
            Toast.makeText(this@ServiceTestActivity, mainService?.test(), Toast.LENGTH_SHORT).show()
        }
    }

    private val handler = Handler()
    private var startForegroundTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_test)
        startService.setOnClickListener {
            startService(Intent(this, MainService::class.java))
        }
        stopService.setOnClickListener {
            stopService(Intent(this, MainService::class.java))
        }
        bindService.setOnClickListener {
            bindService(Intent(this, MainService::class.java), conn, Context.BIND_AUTO_CREATE)
        }
        bindServiceNotAutoCreate.setOnClickListener {
            bindService(Intent(this, MainService::class.java), conn, 0)
        }
        unbindService.setOnClickListener {
            if (isBound) {
                unbindService(conn)
                isBound = false
            }
        }
        startForegroundService.setOnClickListener {
            ContextCompat.startForegroundService(this, Intent(this, MainService::class.java))
            handler.removeCallbacksAndMessages(null)
            startForegroundTime = System.currentTimeMillis()
            handler.postDelayed(object : Runnable {
                override fun run() {
                    tv_time.text = "${System.currentTimeMillis() - startForegroundTime}"
                    handler.postDelayed(this, 500)
                }
            }, 0)
        }
        startForeground.setOnClickListener {
            mainService?.testStartForeground()?.apply {
                Log.d("TAG", "testStartForeground")
                handler.removeCallbacksAndMessages(null)
            }
        }
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        if (isBound) {
            unbindService(conn)
        }
        super.onDestroy()
    }

}