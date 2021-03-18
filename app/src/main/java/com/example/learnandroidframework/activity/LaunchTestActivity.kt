package com.example.learnandroidframework.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import com.example.learnandroidframework.R
import com.example.learnandroidframework.activity.main.SaveInstanceTestActivity
import com.example.learnandroidframework.activity.main.SingleInstanceActivity
import com.example.learnandroidframework.activity.main.SingleTaskActivity
import com.example.learnandroidframework.activity.main.SingleTopActivity
import com.example.learnandroidframework.activity.second.SecondActivity
import kotlinx.android.synthetic.main.activity_launch_test.*

/**
 * Created by mmw on 2021/3/17.
 */
class LaunchTestActivity : AppCompatActivity() {

    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            sendEmptyMessageDelayed(0, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_test)

        startSingleTopActivity.setOnClickListener {
            startActivity(Intent(this, SingleTopActivity::class.java))
        }
        startSingleTaskActivity.setOnClickListener {
            startActivity(Intent(this, SingleTaskActivity::class.java))
        }
        startSecondProcessActivity.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
        startSingleInstanceActivity.setOnClickListener {
            startActivity(Intent(this, SingleInstanceActivity::class.java))
        }
        startNewTaskActivity.setOnClickListener {
//            startActivity(
//                Intent(
//                    this,
//                    NewTaskActivity::class.java
//                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            )
            startActivity(
                Intent(
                    this,
                    SecondActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
        }

        startSaveInstanceActivity.setOnClickListener {
            startActivity(Intent(this, SaveInstanceTestActivity::class.java))
        }
        startActivityByNewDocument.setOnClickListener {
            startActivity(Intent(this, ActivityTestActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
            })
        }
        startActivityByNewDocumentWithMulti.setOnClickListener {
            startActivity(Intent(this, ActivityTestActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
                addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            })
        }

        mHandler.sendEmptyMessageDelayed(0, 1000)



        val windowInset = WindowInsets(null)
        windowInset.getInsets(WindowInsets.Type.ime())
    }
}