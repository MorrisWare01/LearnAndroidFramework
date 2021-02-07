package com.example.learnandroidframework.glide

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.Resource
import com.example.learnandroidframework.R
import kotlinx.android.synthetic.main.activity_glide.*
import java.security.MessageDigest

/**
 * Created by mmw on 2021/2/4.
 */
class GlideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glide)

        btn_app.setOnClickListener {
            Glide.with(this@GlideActivity.applicationContext).pauseAllRequests()
        }
        btn.setOnClickListener {
            Glide.with(this@GlideActivity).pauseAllRequests()
        }
        btn_load.setOnClickListener {
            Glide.with(image)
                .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201304%2F25%2F195133e7a1l7b4f5117y4y.jpg&refer=http%3A%2F%2Fattach.bbs.miui.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1615022557&t=01424b93c6e1c9637c6037c2ffa7a111")
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(object : Transformation<Bitmap?> {
                    override fun updateDiskCacheKey(messageDigest: MessageDigest) {

                    }

                    override fun transform(
                        context: Context,
                        resource: Resource<Bitmap?>,
                        outWidth: Int,
                        outHeight: Int
                    ): Resource<Bitmap?> {
                        SystemClock.sleep(3000)
                        return resource
                    }
                })
                .into(image)
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.container, GlideFragment(), "tag")
            .commitAllowingStateLoss()
    }

    companion object {

        class GlideFragment : Fragment() {

            override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?
            ): View? {
                return ImageView(inflater.context)
            }

            override fun onActivityCreated(savedInstanceState: Bundle?) {
                super.onActivityCreated(savedInstanceState)
                val view = view ?: return
                if (view !is ImageView) return

                Glide.with(view)
                    .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201304%2F25%2F195133e7a1l7b4f5117y4y.jpg&refer=http%3A%2F%2Fattach.bbs.miui.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1615022557&t=01424b93c6e1c9637c6037c2ffa7a111")
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transform(object : Transformation<Bitmap?> {
                        override fun updateDiskCacheKey(messageDigest: MessageDigest) {

                        }

                        override fun transform(
                            context: Context,
                            resource: Resource<Bitmap?>,
                            outWidth: Int,
                            outHeight: Int
                        ): Resource<Bitmap?> {
                            SystemClock.sleep(3000)
                            return resource
                        }
                    })
                    .into(view)
            }
        }
    }


}