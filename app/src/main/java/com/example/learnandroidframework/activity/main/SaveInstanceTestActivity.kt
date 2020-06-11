package com.example.learnandroidframework.activity.main

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * @author mmw
 * @date 2020/5/11
 **/
class SaveInstanceTestActivity : AppCompatActivity() {

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        Log.d("TAG", "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("TAG", "onRestoreInstanceState")
    }

}