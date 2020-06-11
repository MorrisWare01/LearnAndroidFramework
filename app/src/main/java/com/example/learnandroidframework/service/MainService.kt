package com.example.learnandroidframework.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

/**
 * Created by mmw on 2019/10/28.
 */
class MainService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        Log.d("TAG", "onBind")
//        SystemClock.sleep(9000);
//        SystemClock.sleep(21000);
        return IMainService()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d("TAG", "onStartCommand:startId=$startId")
//        return START_NOT_STICKY
//        return START_STICKY
        return START_REDELIVER_INTENT
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("TAG", "onUnbind")
        return true
    }

    override fun onRebind(intent: Intent?) {
        Log.d("TAG", "onRebind")
        super.onRebind(intent)
    }

    override fun onDestroy() {
        Log.d("TAG", "onDestroy")
        super.onDestroy()
    }

    inner class IMainService : Binder() {
        fun test(): String {
            return "test"
        }

        fun testStartForeground() {
            val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channelId = "1"
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    "1",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                nm.createNotificationChannel(channel)
            }
            startForeground(1, NotificationCompat.Builder(baseContext, channelId).build())
        }
    }
}