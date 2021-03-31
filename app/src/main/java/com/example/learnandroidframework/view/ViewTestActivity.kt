package com.example.learnandroidframework.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.learnandroidframework.R
import com.example.learnandroidframework.view.canvas.ViewActivity
import com.example.learnandroidframework.view.viewpager.ViewPagerTestActivity
import kotlinx.android.synthetic.main.activity_view_test.*

/**
 * Created by mmw on 2021/3/31.
 */
class ViewTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_test)

        view_pager.setOnClickListener {
            startActivity(Intent(this, ViewPagerTestActivity::class.java))
        }
        canvas.setOnClickListener {
            startActivity(Intent(this, ViewActivity::class.java))
        }


    }

}