package com.example.learnandroidframework.provider

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.learnandroidframework.R
import kotlinx.android.synthetic.main.activity_provider_test.*
import java.io.File

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
        queryContentProvider.setOnClickListener {
            val cursor = contentResolver.query(
                Uri.parse("content://com.example.provider"),
                arrayOf("text"),
                null,
                null,
                null
            )
            cursor?.apply {
                moveToFirst()
                val text = this.getString(getColumnIndex("text"))
                Toast.makeText(this@ProviderTestActivity, text, Toast.LENGTH_SHORT).show()
                close()
            }
        }
        installApk.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val file = File(getExternalFilesDir("Download"), "demo.apk")
            if (file.exists()) {
                val uri = FileProvider.getUriForFile(this, packageName, file)
                intent.setDataAndType(uri, "application/vnd.android.package-archive")
//                grantUriPermission(
//                    "com.google.android.packageinstaller",
//                    uri,
//                    Intent.FLAG_GRANT_READ_URI_PERMISSION
//                )
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivityForResult(intent, 10)
            } else {
                Toast.makeText(this, "not exist", Toast.LENGTH_SHORT).show()
            }
        }
        startProviderTest.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val file = File(getExternalFilesDir("Download"), "demo.apk")
            if (file.exists()) {
                val uri = FileProvider.getUriForFile(this, packageName, file)
                intent.setDataAndType(uri, "application/vnd.android.package-archive")
//                grantUriPermission("com.google.android.packageinstaller", uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivityForResult(intent, 10)
            } else {
                Toast.makeText(this, "not exist", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10) {
            Log.d("TAG", "resultCode=${resultCode} data=${data}")
        }
    }
}