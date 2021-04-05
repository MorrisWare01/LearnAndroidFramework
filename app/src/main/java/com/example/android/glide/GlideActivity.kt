package com.example.android.glide

import android.graphics.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.model.stream.HttpGlideUrlLoader
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.android.R
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
            // error: requestManager is app scope
            Glide.with(this@GlideActivity.applicationContext).pauseAllRequests()
        }
        btn.setOnClickListener {
            Glide.with(this@GlideActivity).pauseAllRequests()
        }
        btn_load.setOnClickListener {
            Glide.with(image)
                .load(URL)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .set(HttpGlideUrlLoader.TIMEOUT, 10000)
                .transform(object : BitmapTransformation() {
                    override fun updateDiskCacheKey(messageDigest: MessageDigest) {

                    }

                    override fun transform(
                        pool: BitmapPool,
                        toTransform: Bitmap,
                        outWidth: Int,
                        outHeight: Int
                    ): Bitmap {
//                        SystemClock.sleep(3000)
                        return TransformationUtils.circleCrop(
                            pool,
                            toTransform,
                            outWidth,
                            outHeight
                        )
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(image)
        }
        btn_flip.setOnClickListener {
            val matrix = Matrix()
            matrix.postScale(-1f, 1f)
            matrix.postRotate(-90f)

            val bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888)
            val paint = Paint()
            val canvas = Canvas(bitmap)
            canvas.setMatrix(matrix)
            canvas.drawRect(0f, 0f, 50f, 10f, paint.apply {
                color = Color.BLUE
            })
            canvas.drawRect(0f, 10f, 50f, 20f, paint.apply {
                color = Color.RED
            })
            canvas.drawRect(0f, 20f, 50f, 30f, paint.apply {
                color = Color.GRAY
            })
            image.setImageBitmap(bitmap)
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.container, GlideFragment(), "tag")
            .commitAllowingStateLoss()
    }

    companion object {

        //        const val URL =
//            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201304%2F25%2F195133e7a1l7b4f5117y4y.jpg&refer=http%3A%2F%2Fattach.bbs.miui.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1615022557&t=01424b93c6e1c9637c6037c2ffa7a111"
        const val URL = "http://192.168.11.23:8000/images/ic_launcher.png"

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

//                Glide.with(view)
//                    .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201304%2F25%2F195133e7a1l7b4f5117y4y.jpg&refer=http%3A%2F%2Fattach.bbs.miui.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1615022557&t=01424b93c6e1c9637c6037c2ffa7a111")
//                    .skipMemoryCache(true)
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .transform(object : Transformation<Bitmap?> {
//                        override fun updateDiskCacheKey(messageDigest: MessageDigest) {
//
//                        }
//
//                        override fun transform(
//                            context: Context,
//                            resource: Resource<Bitmap?>,
//                            outWidth: Int,
//                            outHeight: Int
//                        ): Resource<Bitmap?> {
//                            SystemClock.sleep(3000)
//                            return resource
//                        }
//                    })
//                    .into(view)
            }
        }
    }


}