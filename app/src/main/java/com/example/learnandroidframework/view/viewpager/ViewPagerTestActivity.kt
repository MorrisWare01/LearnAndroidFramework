package com.example.learnandroidframework.view.viewpager

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.learnandroidframework.R
import kotlinx.android.synthetic.main.activity_view_pager_test.*
import kotlin.math.max
import kotlin.math.min

/**
 * Created by mmw on 2021/3/31.
 */
class ViewPagerTestActivity : AppCompatActivity() {

    val root: ViewGroup by lazy { findViewById<ViewGroup>(R.id.root) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_test)

        container.apply {
            val params = layoutParams
            if (params is ViewGroup.MarginLayoutParams) {
                val margin: Int = (20 * context.resources.displayMetrics.density).toInt()
                params.leftMargin = margin
                params.rightMargin = margin
            }
            setPadding(0, 0, 0, 0)
            adapter = MyPagerAdapter()
//            pageMargin = (context.resources.displayMetrics.density * 10).toInt()
//            setPageMarginDrawable(ColorDrawable(Color.BLACK))
            setPageTransformer(false) { page, position ->
                // position = (child.left - scrollX) / getClientWidth()
                val minScale = 0.8f
                when {
                    // 表示page全部隐藏在屏幕左侧
                    position <= -1 -> {

                    }
                    // 表示page部分隐藏在屏幕左侧
                    position > -1 && position < 0 -> {
                        // 缩小page
                        val scale = max(minScale, position + 1)
                        page.scaleX = scale
                        page.scaleY = scale
                    }
                    // 正在中间
                    position == 0f -> {

                    }
                    // 表示page部分隐藏在屏幕右侧
                    position > 0 && position < 1 -> {
                        // 方法page
                        val scale = max(1 - position, minScale)
                        page.scaleX = scale
                        page.scaleY = scale
                    }
                    // 表示page全部隐藏在屏幕右侧
                    position >= 1 -> {

                    }
                }

            }
        }
    }

    inner class MyPagerAdapter : PagerAdapter() {
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return 5
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            if (position == 0) {
                val linearLayout = LinearLayout(container.context).apply {
                    orientation = LinearLayout.VERTICAL
                    addView(
                        Button(context).apply {
                            text = "clipPadding"
                            setOnClickListener {
                                container.clipToPadding = !container.clipToPadding
                            }
                        }, ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                    )
                    addView(
                        Button(context).apply {
                            text = "clipChildren"
                            setOnClickListener {
                                root.clipChildren = !root.clipChildren
                                if (!root.clipChildren) {
                                    container.clipToPadding = false
                                }
                            }
                        }, ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                    )
                }
                container.addView(
                    linearLayout,
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                )
                return linearLayout
            } else {
                val textView = TextView(container.context).apply {
                    text = position.toString()
                    textSize = context.resources.displayMetrics.density * 20
                    gravity = Gravity.CENTER
                    background = ColorDrawable(Color.parseColor("#f2f2f2"))
                }
                container.addView(
                    textView,
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                )
                return textView
            }
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View?)
        }

        override fun getPageWidth(position: Int): Float {
            return 0.5f
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return "title:$position"
        }
    }

}