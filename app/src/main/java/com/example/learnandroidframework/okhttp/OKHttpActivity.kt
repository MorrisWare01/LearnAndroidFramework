package com.example.learnandroidframework.okhttp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import java.io.IOException

/**
 * Created by mmw on 2021/2/23.
 */
class OKHttpActivity : AppCompatActivity() {

    companion object {
        val okHttpClient: Call.Factory = OkHttpClient.Builder()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        okHttpClient.newCall(
            Request.Builder()
                .build()
        ).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
            }
        })
    }
}