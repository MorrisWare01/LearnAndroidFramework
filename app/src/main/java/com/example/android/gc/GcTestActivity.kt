package com.example.android.gc

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by mmw on 2021/5/10.
 */
class GcTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        for (i in 0..10000) {
            View(this)
        }
    }

}