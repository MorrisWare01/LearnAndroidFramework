package com.example.android.view.recyclerview

import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import kotlinx.android.synthetic.main.activity_recycler_view_test.*

/**
 * Created by mmw on 2021/3/31.
 */
class RecyclerViewTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_test)
        recycer_view.apply {
            layoutManager = LinearLayoutManager(this@RecyclerViewTestActivity)
            adapter = MyAdapter()
        }
    }

    class MyViewHolder(val text: TextView) : RecyclerView.ViewHolder(text)

    class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(TextView(parent.context))
        }

        override fun getItemCount(): Int {
            return 50
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.text.text = position.toString()
        }
    }

}