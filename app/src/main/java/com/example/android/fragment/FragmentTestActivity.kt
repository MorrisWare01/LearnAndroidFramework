package com.example.android.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.example.android.R
import kotlinx.android.synthetic.main.activity_fragment_test.*

/**
 * @author mmw
 * @date 2020/5/20
 **/
class FragmentTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_test)

        val fragment = MainFragment.newInstance("Root")
        supportFragmentManager.beginTransaction()
            .add(R.id.content, fragment)
            .add(R.id.content, Fragment())
            .setMaxLifecycle(fragment, Lifecycle.State.CREATED)
//            .setPrimaryNavigationFragment(fragment)
            .setReorderingAllowed(false)
//            .addToBackStack("A")
            .commitAllowingStateLoss()

        supportFragmentManager.popBackStack()

        show.setOnClickListener {
            MainDialogFragment()
                .show(supportFragmentManager, "123")
        }
    }

}