package com.example.android.receiver

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.R
import kotlinx.android.synthetic.main.activity_broadcast_receivice_test.*

/**
 * @author mmw
 * @date 2020/6/1
 **/
class BroadcastReceiverTestActivity : AppCompatActivity() {

    private lateinit var receiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast_receivice_test)
        sendComponentBroadcast.setOnClickListener {
//            sendBroadcast(Intent(this, MainBroadcastReceiver::class.java))
            sendBroadcast(Intent("com.example.receiver").setPackage(packageName))
        }
        sendRegisterBroadcast.setOnClickListener {
            sendBroadcast(Intent("com.example.data"))
        }
        sendOrderBroadcast.setOnClickListener {
            sendBroadcast(Intent(this, MainBroadcastReceiver::class.java))
            sendOrderedBroadcast(Intent("com.example.data"), null)
        }
        sendStickyBroadcast.setOnClickListener {
            sendStickyBroadcast(Intent("com.example.data"))
        }
        registerBroadcast.setOnClickListener {
            receiver = RegisterBroadcastReceiver()
            registerReceiver(receiver, IntentFilter("com.example.data"))
        }
        anrBroadcast.setOnClickListener {
            sendBroadcast(Intent(this, AnrBroadcastReceiver::class.java))
        }
    }

    override fun onDestroy() {
        if (this::receiver.isInitialized) {
            unregisterReceiver(receiver)
        }
        super.onDestroy()
    }

}