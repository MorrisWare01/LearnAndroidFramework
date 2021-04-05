package com.example.android.activity.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.R
import kotlinx.android.synthetic.main.activity_single_instance.*

/**
 * @author mmw
 * @date 2020/4/14
 **/
class SingleInstanceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_instance)
        startSingleInstance1.setOnClickListener {
            startActivity(Intent(this, SingleInstance1Activity::class.java))
        }
    }
}