package com.example.learnandroidframework.moblink

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.learnandroidframework.SplashActivity
import com.mob.moblink.MobLink
import com.mob.moblink.Scene
import com.mob.moblink.SceneRestorable


/**
 * @author mmw
 * @date 2020/7/1
 **/
class MobLinkActivity : AppCompatActivity(), SceneRestorable {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isTaskRoot) {
            startActivity(Intent(this, SplashActivity::class.java))
        }
        finish()
    }

    // 必须重写该方法，防止MobLink在某些情景下无法还原
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        MobLink.updateNewIntent(getIntent(), this)
        finish()
    }

    override fun onResume() {
        finish()
        super.onResume()
    }

    override fun onReturnSceneData(p0: Scene?) {
        val path = p0?.path
        val params = p0?.params
        Log.d("TAG", "onReturnSceneData: $path-$params")
    }
}