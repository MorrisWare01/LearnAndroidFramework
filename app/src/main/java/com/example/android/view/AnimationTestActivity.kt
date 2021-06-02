package com.example.android.view

import android.os.Bundle
import android.view.animation.RotateAnimation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android.R
import kotlinx.android.synthetic.main.activity_animation_test.*

/**
 * Created by mmw on 2021/5/10.
 */
class AnimationTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation_test)

        anchor.setOnClickListener {
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
        }

//        val animation = RotateAnimation(
//            0f, 45f,
//            RotateAnimation.RELATIVE_TO_SELF,
//            0.5f,
//            RotateAnimation.RELATIVE_TO_SELF,
//            0.5f
//        )
//        anchor.animation = animation
//        animation.duration = 3000
////        animation.fillAfter = true
//        animation.fillBefore = true
//        animation.start()

        anchor.animate().rotation(45f).apply {
            duration = 3000
            start()
        }
    }
}