package com.example.learnandroidframework.activity.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.learnandroidframework.R
import com.example.learnandroidframework.activity.second.OtherSingleTaskActivity
import kotlinx.android.synthetic.main.activity_single_top.*

/**
 * @author mmw
 * @date 2020/7/14
 **/
class SingleTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAG", "onCreate")
        setContentView(R.layout.activity_single_top)
        startMe.setOnClickListener {
            startActivity(Intent(this, SingleTaskActivity::class.java))
        }
        startMeWithDiffTask.setOnClickListener {
            startActivity(Intent(this, OtherSingleTaskActivity::class.java))
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("TAG", "onNewIntent")
    }

}