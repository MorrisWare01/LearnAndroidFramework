package com.example.learnandroidframework

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.learnandroidframework.activity.ActivityTestActivity
import com.example.learnandroidframework.fragment.FragmentTestActivity
import com.example.learnandroidframework.fragment.NavigationTestActivity
import com.example.learnandroidframework.provider.ProviderTestActivity
import com.example.learnandroidframework.service.MainService
import com.example.learnandroidframework.receiver.BroadcastReceiverTestActivity
import com.example.learnandroidframework.service.ServiceTestActivity
import com.example.learnandroidframework.view.ViewActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("MAIN", getAndroidId().toString())
        startActivityTest.setOnClickListener {
            startActivity(Intent(this, ActivityTestActivity::class.java))
        }
        serviceTest.setOnClickListener {
            startActivity(Intent(this, ServiceTestActivity::class.java))
        }
        broadcastTest.setOnClickListener {
            startActivity(Intent(this, BroadcastReceiverTestActivity::class.java))
        }
        providerTest.setOnClickListener {
            startActivity(Intent(this, ProviderTestActivity::class.java))
        }
        showToastWindow.setOnClickListener {
            Toast.makeText(this, "toast window", Toast.LENGTH_LONG).show()
        }
        showDialogWindow.setOnClickListener {
            val dialog = Dialog(this)
            val textView = TextView(this)
            textView.setText("123")
            dialog.setContentView(textView, WindowManager.LayoutParams(-1, -1))
            dialog.window?.setGravity(Gravity.CENTER)
            dialog.show()
        }
        startViewActivity.setOnClickListener {
            startActivity(Intent(this, ViewActivity::class.java))
        }
        startFragmentTestActivity.setOnClickListener {
            startActivity(Intent(this, FragmentTestActivity::class.java))
        }
        startNavigationTestActivity.setOnClickListener {
            startActivity(Intent(this, NavigationTestActivity::class.java))
        }
    }

    @SuppressLint("HardwareIds")
    fun getAndroidId(): String? {
        return Settings.Secure.getString(
            application.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }
}
