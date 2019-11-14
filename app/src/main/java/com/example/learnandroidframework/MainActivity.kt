package com.example.learnandroidframework

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var isBound = false
    private val conn = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            isBound = true
            val mainService = service as MainService.IMainService
            Toast.makeText(this@MainActivity, mainService.test(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("MAIN", getAndroidId().toString())
        startActivity.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
        startActivityResult.setOnClickListener {
            startActivityForResult(Intent(this, SecondActivity::class.java), 10)
        }
        startService.setOnClickListener {
            startService(Intent(this, MainService::class.java))
        }
        bindService.setOnClickListener {
            bindService(Intent(this, MainService::class.java), conn, Context.BIND_AUTO_CREATE)
        }
        sendBroadcast.setOnClickListener {
            sendBroadcast(Intent(this, MainBroadcastReceiver::class.java))
        }
        queryContentProvider.setOnClickListener {
            val cursor = contentResolver.query(
                Uri.parse("content://com.example.provider"),
                arrayOf("text"),
                null,
                null,
                null
            )
            cursor?.apply {
                moveToFirst()
                val text = this.getString(getColumnIndex("text"))
                Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
                close()
            }
        }
    }

    override fun onDestroy() {
        if (isBound) {
            unbindService(conn)
        }
        super.onDestroy()
    }

    @SuppressLint("HardwareIds")
    fun getAndroidId(): String? {
        return Settings.Secure.getString(
            application.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }
}
