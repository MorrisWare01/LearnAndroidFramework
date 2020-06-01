package com.example.learnandroidframework

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.learnandroidframework.fragment.FragmentTestActivity
import com.example.learnandroidframework.fragment.NavigationTestActivity
import com.example.learnandroidframework.process.main.receiver.MainBroadcastReceiver
import com.example.learnandroidframework.process.main.service.MainService
import com.example.learnandroidframework.provider.ProviderTestActivity
import com.example.learnandroidframework.view.ViewActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class SplashActivity : AppCompatActivity() {

    private var isBound = false
    private val conn = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            isBound = true
            val mainService = service as MainService.IMainService
            Toast.makeText(this@SplashActivity, mainService.test(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("MAIN", getAndroidId().toString())
        startActivityTest.setOnClickListener {
            startActivity(Intent(this, ActivityTestActivity::class.java))
        }
        startService.setOnClickListener {
            startService(Intent(this, MainService::class.java))
        }
        bindService.setOnClickListener {
            bindService(Intent(this, MainService::class.java), conn, Context.BIND_AUTO_CREATE)
        }
        sendBroadcast.setOnClickListener {
            sendBroadcast(
                Intent(this, MainBroadcastReceiver::class.java)
                    .putExtra("text", "test")
            )
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
                Toast.makeText(this@SplashActivity, text, Toast.LENGTH_SHORT).show()
                close()
            }
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
        installApk.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val file = File(getExternalFilesDir("Download"), "demo.apk")
            if (file.exists()) {
                val uri = FileProvider.getUriForFile(this, packageName, file)
                intent.setDataAndType(uri, "application/vnd.android.package-archive")
//                grantUriPermission(
//                    "com.google.android.packageinstaller",
//                    uri,
//                    Intent.FLAG_GRANT_READ_URI_PERMISSION
//                )
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivityForResult(intent, 10)
            } else {
                Toast.makeText(this, "not exist", Toast.LENGTH_SHORT).show()
            }
        }
        startProviderTest.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val file = File(getExternalFilesDir("Download"), "demo.apk")
            if (file.exists()) {
                val uri = FileProvider.getUriForFile(this, packageName, file)
                intent.setDataAndType(uri, "application/vnd.android.package-archive")
//                grantUriPermission("com.google.android.packageinstaller", uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivityForResult(intent, 10)
            } else {
                Toast.makeText(this, "not exist", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10) {
            Log.d("TAG", "resultCode=${resultCode} data=${data}")
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
