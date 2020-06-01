package com.example.learnandroidframework

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.example.learnandroidframework.process.main.activity.NewTaskActivity
import com.example.learnandroidframework.process.main.activity.SaveInstanceTestActivity
import com.example.learnandroidframework.process.main.activity.SingleInstanceActivity
import com.example.learnandroidframework.process.second.SecondActivity
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
    }

}