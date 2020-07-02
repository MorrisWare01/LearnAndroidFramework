package com.example.learnandroidframework.native

import android.os.IBinder

/**
 * @author mmw
 * @date 2020/6/30
 **/
object Native {

    init {
        System.loadLibrary("native")
    }

    external fun getBinderProxyHandle(binder: IBinder): Long

}

