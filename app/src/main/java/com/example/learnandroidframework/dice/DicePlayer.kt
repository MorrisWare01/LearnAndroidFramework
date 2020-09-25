package com.example.learnandroidframework.dice

/**
 * Created by mmw on 2020/9/25.
 */
data class DicePlayer(
    var chess: DiceChess? = null,

    var isRunning: Boolean = false,

    var scaleX: Float = 1f,
    var scaleY: Float = 1f,
    var translateX: Float = 0f,
    var translateY: Float = 0f
)