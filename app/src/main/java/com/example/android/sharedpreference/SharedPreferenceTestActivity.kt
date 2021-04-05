package com.example.android.sharedpreference

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * @author mmw
 * @date 2020/6/11
 **/
class SharedPreferenceTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preference = getSharedPreferences("learn", Context.MODE_PRIVATE)
        preference.edit().putString("apply", "value").apply()
        preference.edit().putString("commit", "value").commit()
        Log.d("TAG", "apply = ${preference.getString("apply", "none")}")
        Log.d("TAG", "commit = ${preference.getString("commit", "none")}")
    }
}