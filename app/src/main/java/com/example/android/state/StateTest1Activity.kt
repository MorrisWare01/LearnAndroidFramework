package com.example.android.state

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


/**
 * Created by mmw on 2021/5/19.
 */
class StateTest1Activity : AppCompatActivity() {

    companion object {
        var callback: (() -> Unit)? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Flowable.timer(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                callback?.invoke()
                finish()
            }
    }

}