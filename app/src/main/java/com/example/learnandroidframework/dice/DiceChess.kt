package com.example.learnandroidframework.dice

import android.graphics.Rect

/**
 * Created by mmw on 2020/9/25.
 */
data class DiceChess(
    val rect: Rect,
    var type: String? = null,
    var consumed: Boolean = false
) {

    companion object {
        const val TYPE_RED_BAG = "red_bag"
        const val TYPE_COIN = "coin"
        const val TYPE_TRAP = "trap"
        const val TYPE_EVENT = "event"

    }

}