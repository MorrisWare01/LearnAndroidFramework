package com.example.android.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.android.R
import com.example.android.view.canvas.ViewActivity
import com.example.android.view.lifecycle.ViewLifecycleActivity
import com.example.android.view.recyclerview.RecyclerViewTestActivity
import com.example.android.view.viewpager.ViewPagerTestActivity
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_view_test.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

/**
 * Created by mmw on 2021/3/31.
 */
class ViewTestActivity : AppCompatActivity() {

    private var disposable: Disposable? = null
    private val headerAnimatorQueue = ArrayDeque<Item>()
    private val footerAnimatorQueue = ArrayDeque<Item>()
    private var waitAddItems: List<String>? = null
    private var isHeaderRunning = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_test)

        view_pager.setOnClickListener {
            startActivity(Intent(this, ViewPagerTestActivity::class.java))
        }
        recycer_view.setOnClickListener {
            startActivity(Intent(this, RecyclerViewTestActivity::class.java))
        }
        canvas.setOnClickListener {
            startActivity(Intent(this, ViewActivity::class.java))
        }
        lifecycle_test.setOnClickListener {
            startActivity(Intent(this, ViewLifecycleActivity::class.java))
        }
        animation.setOnClickListener {
            startActivity(Intent(this, AnimationTestActivity::class.java))
        }
        simulator.setOnClickListener {
            show(listOf("A", "B", "C", "D"))
        }
        simulator_empty.setOnClickListener {
            show(null)
        }
    }

    private fun setInterval(delay: Long, time: Long, callback: () -> Unit): Disposable {
        return Flowable.interval(delay, time, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { callback() }
    }

    private fun setTimeOut(time: Long, callback: (() -> Unit)): Disposable {
        return Flowable.timer(time, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { callback() }
    }

    private fun dp2Px(dp: Float): Float {
        return resources.displayMetrics.density * dp
    }

    private fun inflateView(layout: ViewGroup, item: Item?): View {
        return layoutInflater.inflate(R.layout.layout_item, layout, false).apply {
            if (this is TextView) {
                this.text = item?.text
            }
            this.tag = item
        }
    }

    data class Item(
        val text: String?
    )

    private fun show(
        data: List<String>?,
        animatorDuration: Long = 1000,
        showDuration: Long = 1000
    ) {
        waitAddItems = data
        if (!data.isNullOrEmpty()) {
            // 启动动画
            startInterval(animatorDuration, showDuration)
        }
    }

    private fun fillQueueData(queue: ArrayDeque<Item>, data: List<String>) {
        data.forEach { item ->
            queue.offer(Item(item))
        }
        while (queue.size in 1..3) {
            data.forEach { item ->
                queue.offer(Item(item))
                if (queue.size >= 4) {
                    return@forEach
                }
            }
        }
    }

    private fun startInterval(animatorDuration: Long = 1000, showDuration: Long = 1000) {
        // 循环播放
        if (disposable == null) {
            disposable =
                setInterval(0, showDuration + animatorDuration) {
                    val layout = if (isHeaderRunning) layout_header else layout_footer
                    val queue = if (isHeaderRunning) headerAnimatorQueue else footerAnimatorQueue

                    // 默认添加4个View
                    if (layout.childCount == 0) {
                        // 添加数据
                        queue.clear()
                        val items = waitAddItems
                        if (items.isNullOrEmpty()) {
                            disposable?.dispose()
                            disposable = null
                            layout.visibility = View.GONE
                            return@setInterval
                        }
                        fillQueueData(queue, items)
                        // 展示
                        layout.visibility = View.VISIBLE
                        for (i in 0..3) {
                            layout.addView(inflateView(layout, queue.poll()).apply {
                                when (i) {
                                    3 -> {
                                        scaleX = 0.8f
                                        scaleY = 0.8f
                                        translationY = dp2Px(30f)
                                        alpha = 0f
                                    }
                                    2 -> {
                                        scaleX = 0.8f
                                        scaleY = 0.8f
                                        translationY = dp2Px(30f)
                                        alpha = 0.3f
                                    }
                                    1 -> {
                                        scaleX = 0.88f
                                        scaleY = 0.88f
                                        translationY = dp2Px(15f)
                                        alpha = 0.3f
                                    }
                                }
                            }, 0)
                        }
                        return@setInterval
                    }

                    // 判断当前是否已经是最后一个Item
                    val nextTop = layout.getChildAt(layout.childCount - 2)
                    if (nextTop != null) {
                        val tag = nextTop.tag
                        if (tag == null) {
                            if (isHeaderRunning) {
                                isHeaderRunning = false
                                container.translationY = layout.height.toFloat()
                                container.animate().translationY(0f)
                                    .setDuration(500)
                                    .start()

                                disposable?.dispose()
                                disposable = null
                                startInterval(animatorDuration, showDuration)
                            } else {
                                disposable?.dispose()
                                disposable = null
                            }
                            layout.removeAllViews()
                            layout.visibility = View.GONE
                            return@setInterval
                        }
                    }

                    // 执行动画
                    val animatorSet = AnimatorSet()
                    val animators = ArrayList<Animator>()
                    for (i in layout.childCount - 1 downTo 0) {
                        val view = layout.getChildAt(i)
                        when (i) {
                            3 -> {
                                val destX =
                                    -view.width.toFloat() - dp2Px(15f)
                                animators.add(
                                    ObjectAnimator.ofFloat(
                                        view,
                                        View.TRANSLATION_X.name,
                                        destX
                                    )
                                )
                            }
                            2 -> {
                                animators.add(
                                    ObjectAnimator.ofFloat(
                                        view,
                                        View.TRANSLATION_Y.name,
                                        0f
                                    )
                                )
                                animators.add(ObjectAnimator.ofFloat(view, View.SCALE_X, 1f))
                                animators.add(ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f))
                                animators.add(ObjectAnimator.ofFloat(view, View.ALPHA, 1f))
                            }
                            1 -> {
                                animators.add(
                                    ObjectAnimator.ofFloat(
                                        view,
                                        View.TRANSLATION_Y.name,
                                        dp2Px(15f)
                                    )
                                )
                                animators.add(ObjectAnimator.ofFloat(view, View.SCALE_X, 0.88f))
                                animators.add(ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.88f))
                            }
                            0 -> {
                                animators.add(ObjectAnimator.ofFloat(view, View.ALPHA, 0.3f))
                            }
                        }
                    }
                    animatorSet.playTogether(animators)
                    animatorSet.duration = animatorDuration
                    animatorSet.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            // 移除顶部的View
                            layout.removeViewAt(layout.childCount - 1)
                            // 添加新的底部View
                            var item = queue.poll()
                            if (item == null && !isHeaderRunning) {
                                val waitItems = waitAddItems
                                if (!waitItems.isNullOrEmpty()) {
                                    fillQueueData(queue, waitItems)
                                }
                                item = queue.poll()
                            }
                            layout.addView(inflateView(layout, item).apply {
                                scaleX = 0.8f
                                scaleY = 0.8f
                                translationY = dp2Px(30f)
                                alpha = 0f
                            }, 0)
                        }
                    })
                    animatorSet.start()
                }
        }
    }

}