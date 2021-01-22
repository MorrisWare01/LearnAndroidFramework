package com.example.learnandroidframework.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_fragment_test.view.*

/**
 * @author mmw
 * @date 2020/5/20
 **/
class MainFragment : Fragment() {

    companion object {
        fun newInstance(name: String?) = MainFragment().apply {
            arguments = Bundle().apply {
                putString("name", name)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("TAG", "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAG", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TAG", "onCreateView")
        return FrameLayout(layoutInflater.context).apply {
            id = android.R.id.text1
            setBackgroundColor(Color.RED)
            addView(TextView(layoutInflater.context).apply {
                text = arguments?.getString("name")
            })
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewId = view?.id ?: android.R.id.text1
        Log.d("TAG", "onActivityCreated")
        val text = arguments?.getString("name")
        if ("A" == text) {
            val mainFragment = newInstance("AA")
            childFragmentManager.beginTransaction()
                .add(viewId, newInstance("B"), "B")
                .add(viewId, newInstance("C"), "C")
                .add(viewId, mainFragment, "AA")
//                .setPrimaryNavigationFragment(mainFragment)
                .addToBackStack("AA")
                .commitAllowingStateLoss()
        } else if ("AA" == text) {
            val mainFragment = newInstance("AAA")
            childFragmentManager.beginTransaction()
                .add(viewId, newInstance("BB"), "BB")
                .add(viewId, newInstance("CC"), "CC")
                .add(viewId, mainFragment, "AAA")
//                .setPrimaryNavigationFragment(mainFragment)
                .addToBackStack("AAA")
                .commitAllowingStateLoss()
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG", "onResume")
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {

        return super.onCreateAnimation(transit, enter, nextAnim)
    }

}