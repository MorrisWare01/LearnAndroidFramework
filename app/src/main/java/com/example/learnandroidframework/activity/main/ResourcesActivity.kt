package com.example.learnandroidframework.activity.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.learnandroidframework.R
import kotlinx.android.synthetic.main.activity_resources.*

/**
 * Created by mmw on 2021/3/24.
 */
class ResourcesActivity : AppCompatActivity() {

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resources)


        test.setOnClickListener {
            testDrawableCache()
        }
    }

    private fun testDrawableCache() {
        iv_left.setImageDrawable(resources.getDrawable(R.drawable.colorDrawable, theme))
        iv_right.setImageDrawable(resources.getDrawable(R.drawable.colorDrawable, theme))

        handler.postDelayed({
            val newDrawable = resources.getDrawable(R.drawable.colorDrawable, theme)
                .constantState?.newDrawable()?.apply {
                    if (this is ColorDrawable) {
                        this.color = Color.RED
                    }
                }
            iv_left.setImageDrawable(resources.getDrawable(R.drawable.colorDrawable, theme))
            iv_right.setImageDrawable(newDrawable)
        }, 500)

        handler.postDelayed({
            do {
                iv_left.setImageDrawable(resources.getDrawable(R.drawable.colorDrawable, theme))
                iv_right.setImageDrawable(resources.getDrawable(R.drawable.colorDrawable, theme))

                val newDrawable = resources.getDrawable(R.drawable.colorDrawable, theme)
                    .constantState?.newDrawable()?.apply {
                        if (this is ColorDrawable) {
                            mutate()
                            this.color = Color.BLUE
                        }
                    }
                iv_left.setImageDrawable(resources.getDrawable(R.drawable.colorDrawable, theme))
                iv_right.setImageDrawable(newDrawable)
            } while (false)

        }, 1000)
    }


}