package com.example.android.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import com.example.android.R
import com.example.android.activity.main.*
import com.example.android.activity.second.SecondActivity
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
            // 在当前Task添加activity
            startActivity(
                Intent(
                    this,
                    NewTaskActivity::class.java
                )
            )
            // 新建一个Task添加activity
//            startActivity(
//                Intent(
//                    this,
//                    NewTaskActivity::class.java
//                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            )
            // 清除Task再添加activity
//            startActivity(
//                Intent(
//                    this,
//                    SecondActivity::class.java
//                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            )
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
    }
}