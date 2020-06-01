package com.example.learnandroidframework

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.util.Log

/**
 * @author mmw
 * @date 2020/5/11
 **/
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("App", "onCreate:${getCurrentProcessName()}")
        registerActivityLifecycleCallbacks(MyActivityLifecycleCallback())
    }

    private fun getCurrentProcessName(): String? {
        val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (appProcess in am.runningAppProcesses) {
            if (appProcess.pid == Process.myPid()) {
                return appProcess.processName
            }
        }
        return null
    }

    class MyActivityLifecycleCallback : ActivityLifecycleCallbacks {

        private var isInited = false

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            if (savedInstanceState != null && !isInited) {
                isInited = true
                activity.startActivity(Intent(activity, TestSplashActivity::class.java))
            }
        }

        override fun onActivityStarted(activity: Activity) {
        }

        override fun onActivityResumed(activity: Activity) {
        }

        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityDestroyed(activity: Activity) {
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityStopped(activity: Activity) {
        }
    }

}