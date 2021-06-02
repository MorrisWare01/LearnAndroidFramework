package com.example.android.view.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.R
import kotlinx.android.synthetic.main.activity_view_lifecycle.*

/**
 * Created by mmw on 2021/5/7.
 */
class ViewLifecycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_lifecycle)

        invalid.setOnClickListener {
            group1.invalidate()
//            group1_view0.invalidate()
        }

    }
}