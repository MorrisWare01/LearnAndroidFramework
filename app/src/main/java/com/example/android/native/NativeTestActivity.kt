package com.example.android.native

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android.R
import kotlinx.android.synthetic.main.activity_native_test.*
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by mmw on 2021/4/5.
 */
class NativeTestActivity : AppCompatActivity() {

    private val executor = ThreadPoolExecutor(
        0,
        Int.MAX_VALUE,
        10,
        TimeUnit.SECONDS,
        SynchronousQueue<Runnable>()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native_test)
        test.setOnClickListener {
            executor.execute {
                Native.test()
            }
        }
        Toast.makeText(this, "${Native.getData()}", Toast.LENGTH_SHORT).show()
    }

}