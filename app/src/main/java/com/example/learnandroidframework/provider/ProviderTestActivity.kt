package com.example.learnandroidframework.provider

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.learnandroidframework.R
import kotlinx.android.synthetic.main.activity_provider_test.*
import java.io.FileInputStream
import java.io.InputStream

/**
 * @author mmw
 * @date 2020/5/29
 **/
class ProviderTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider_test)
        btn_query.setOnClickListener {
//            val uri = Uri.Builder()
//                .scheme(ContentResolver.SCHEME_CONTENT)
//                .authority("com.xiqu.box.fileProvider")
//                .appendPath("adwall_external_root")
//                .appendPath("Download/demo.apk")
//                .build()
//            contentResolver.openInputStream(uri)
        }
    }

}