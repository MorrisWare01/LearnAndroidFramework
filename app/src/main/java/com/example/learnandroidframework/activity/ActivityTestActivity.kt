package com.example.learnandroidframework.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.learnandroidframework.R
import com.example.learnandroidframework.SplashActivity
import com.example.learnandroidframework.activity.main.*
import com.example.learnandroidframework.activity.second.SecondActivity
import kotlinx.android.synthetic.main.activity_activity_test.*

/**
 * @author mmw
 * @date 2020/4/21
 **/
class ActivityTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity_test)
        startActivityResult.setOnClickListener {
            startActivityForResult(Intent(this, SecondActivity::class.java), 10)
        }
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
            startActivity(
                Intent(
                    this,
                    NewTaskActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
        startPickerActivity.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
//            intent.data = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            startActivity(intent)
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
        startFullscreenActivity.setOnClickListener {
            startActivity(Intent(this, FullscreenActivity::class.java))
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("TAG", "onNewIntent")
    }

}