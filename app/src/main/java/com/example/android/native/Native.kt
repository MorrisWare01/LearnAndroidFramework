package com.example.android.native

/**
 * @author mmw
 * @date 2020/6/30
 **/
object Native {

    private const val data = 100

    init {
        System.loadLibrary("native")
    }

    @JvmStatic
    external fun getData(): Int

}

