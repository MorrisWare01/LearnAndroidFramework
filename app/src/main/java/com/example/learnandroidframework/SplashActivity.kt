package com.example.learnandroidframework

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.learnandroidframework.activity.ActivityTestActivity
import com.example.learnandroidframework.dice.DiceActivity
import com.example.learnandroidframework.fragment.FragmentTestActivity
import com.example.learnandroidframework.fragment.NavigationTestActivity
import com.example.learnandroidframework.provider.ProviderTestActivity
import com.example.learnandroidframework.receiver.BroadcastReceiverTestActivity
import com.example.learnandroidframework.service.ServiceTestActivity
import com.example.learnandroidframework.sharedpreference.SharedPreferenceTestActivity
import com.example.learnandroidframework.view.ViewActivity
import kotlinx.android.synthetic.main.activity_main.*

class SplashActivity : AppCompatActivity() {

    @SuppressLint("DiscouragedPrivateApi", "PrivateApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("MAIN", getAndroidId().toString())
        val intent = intent
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
        sharedPreferenceTest.setOnClickListener {
            startActivity(Intent(this, SharedPreferenceTestActivity::class.java))
        }
        startViewActivity.setOnClickListener {
            startActivity(Intent(this, ViewActivity::class.java))
        }
        startDiceActivity.setOnClickListener {
            startActivity(Intent(this, DiceActivity::class.java))
        }
        binderTest.setOnClickListener {
            try {
                val serviceManager = Class.forName("android.os.ServiceManager")
                val listServicesMethod = serviceManager.getDeclaredMethod("listServices")
                val result = listServicesMethod.invoke(null) as Array<*>
                Log.e("TAG", result.contentToString())

                val getServiceMethod = serviceManager.getDeclaredMethod(
                    "getService",
                    String::class.java
                )
                val am = getServiceMethod.invoke(null, "activity")
                Log.e("TAG", am.toString())

                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                    val addServiceMethod = serviceManager.getDeclaredMethod(
                        "addService",
                        String::class.java,
                        IBinder::class.java
                    )
                    addServiceMethod.invoke(null, "test", object : IMyAidlInterface.Stub() {
                        override fun publishBinder(binder: IBinder?) {

                        }

                        override fun ring() {
                            Log.e("TAG", "ring")
                        }
                    })

                    val remote = getServiceMethod.invoke(null, "test") as IBinder?
                    Log.e("TAG", "remote:" + remote.toString())
                    IMyAidlInterface.Stub.asInterface(remote)?.ring()
                }
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
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
        startFragmentTestActivity.setOnClickListener {
            startActivity(Intent(this, FragmentTestActivity::class.java))
        }
        startNavigationTestActivity.setOnClickListener {
            startActivity(Intent(this, NavigationTestActivity::class.java))
        }
        lottieTest.setOnClickListener {
//            startActivity(Intent(this, LottieTestActivity::class.java))
            try {
                val packageName = "com.huawei.hwid"
                if (isApkInstalled(this, packageName)) {
                    startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.parse("package:$packageName")
                    })
                } else {
                    startActivity(Intent(Settings.ACTION_SETTINGS))
                }
            } catch (e: Exception) {
                Log.e("TAG", e.message ?: "")
            }
        }
        Log.d("TAG", Build.MANUFACTURER)
    }

    fun isApkInstalled(context: Context, packageName: String): Boolean {
        // 获取packagemanager
        val packageManager = context.packageManager
        try {
            packageManager.getPackageInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            return false
        }
        return true
    }

    @SuppressLint("HardwareIds")
    fun getAndroidId(): String? {
        return Settings.Secure.getString(
            application.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }
}
