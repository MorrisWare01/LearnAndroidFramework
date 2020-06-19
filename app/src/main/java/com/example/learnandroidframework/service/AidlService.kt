package com.example.learnandroidframework.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.learnandroidframework.IMyAidlInterface

/**
 * Created by mmw on 2019/10/28.
 */
class AidlService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return TransferInterface {
            Log.d("TAG", "aidl service ring")
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("TAG", "aidl service intent${intent}")
        return super.onStartCommand(intent, flags, startId)
    }

    class TransferInterface(private val func: () -> Unit) : IMyAidlInterface.Stub() {

        private var mRemote: IBinder? = null

        override fun publishBinder(binder: IBinder?) {
            Log.d("TAG", "publishBinder：${binder?.interfaceDescriptor}}")
            mRemote = binder
        }

        override fun ring() {
            asInterface(mRemote)?.apply {
                Log.d("TAG", "call mRemote ring")
                ring()
            } ?: apply {
                func()
            }
        }
    }
}