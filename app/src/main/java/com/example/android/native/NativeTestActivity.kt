package com.example.android.native

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by mmw on 2021/4/5.
 */
class NativeTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "${Native.getData()}", Toast.LENGTH_SHORT).show()
    }

}