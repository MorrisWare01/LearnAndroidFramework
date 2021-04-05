package com.example.android.activity

import android.app.PictureInPictureParams
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android.R
import com.example.android.activity.main.ConfigurationActivity
import com.example.android.activity.main.FullscreenActivity
import com.example.android.activity.main.NewTaskActivity
import com.example.android.activity.main.ResourcesActivity
import com.example.android.activity.second.SecondActivity
import kotlinx.android.synthetic.main.activity_activity_test.*

/**
 * @author mmw
 * @date 2020/4/21
 **/
class ActivityTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity_test)
        startLaunchTestActivity.setOnClickListener {
            // 启动标志
            startActivity(Intent(this, LaunchTestActivity::class.java))
        }
        startActivityResult.setOnClickListener {
            startActivityForResult(Intent(this, SecondActivity::class.java), 10)
        }
        startPickerActivity.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
//            intent.data = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            startActivity(intent)
        }
        startFullscreenActivity.setOnClickListener {
            // systemUiVisibility
            startActivity(Intent(this, FullscreenActivity::class.java))
        }
        startTaskOnHomeActivity.setOnClickListener {
            startActivity(Intent(this, NewTaskActivity::class.java).apply {
                // 如果是新的task，点击返回键就返回桌面
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
            })
        }
        enterPipMode.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                enterPictureInPictureMode(
                    PictureInPictureParams.Builder().build()
                )
            }
        }
        enterMultiWindow.setOnClickListener {
        }
        configurationTest.setOnClickListener {
            startActivity(Intent(this, ConfigurationActivity::class.java))
        }
        resourcesTest.setOnClickListener {
            startActivity(Intent(this, ResourcesActivity::class.java))
        }
        openBrower.setOnClickListener {
            val url = "https://www.baidu.com"

            // android R以上 需要配置queries
            val intent = Intent()
            intent.action = "android.intent.action.VIEW"
            intent.data = Uri.parse(url)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(Intent.createChooser(intent, "请选择浏览器"))
            } else {
                Toast.makeText(this, "没有可用的浏览器", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("TAG", "onNewIntent")
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.d("TAG", "onResume => isInPictureInPictureMode:$isInPictureInPictureMode")
        }
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.d("TAG", "onPause => isInPictureInPictureMode:$isInPictureInPictureMode")
        }
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration?
    ) {
        Log.d(
            "TAG",
            "onPictureInPictureModeChanged => isInPictureInPictureMode:$isInPictureInPictureMode"
        )
    }

}