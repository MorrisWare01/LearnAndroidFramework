package com.example.learnandroidframework.dice

import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.learnandroidframework.R
import kotlin.random.Random

/**
 * Created by mmw on 2020/9/25.
 */
class DiceActivity : AppCompatActivity() {

    private val mHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        setContentView(R.layout.activity_dice)

        val diceView = findViewById<DiceView>(R.id.dice_view)
        val ivDice = findViewById<ImageView>(R.id.iv_dice)
        val diceAnimDrawable =
            ContextCompat.getDrawable(this, R.drawable.dice_anim_loading) as AnimationDrawable
        findViewById<View>(R.id.btn_go).setOnClickListener {
            if (diceAnimDrawable.isRunning || diceView.isPlayerRunning()) {
                return@setOnClickListener
            }

            diceAnimDrawable.start()
            ivDice.setImageDrawable(diceAnimDrawable)

            mHandler.postDelayed({
                diceAnimDrawable.stop()
                val num = Random.nextInt(5) + 1
                ivDice.setImageResource(
                    when (num) {
                        1 -> R.mipmap.dice_1
                        2 -> R.mipmap.dice_2
                        3 -> R.mipmap.dice_3
                        4 -> R.mipmap.dice_4
                        5 -> R.mipmap.dice_5
                        6 -> R.mipmap.dice_6
                        else -> 0
                    }
                )
                diceView.go(num)
            }, (1000 + Math.random() * 1000f).toLong())
        }
    }

}