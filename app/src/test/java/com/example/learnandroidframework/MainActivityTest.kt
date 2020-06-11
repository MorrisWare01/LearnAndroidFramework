package com.example.learnandroidframework

import android.content.ComponentName
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.learnandroidframework.activity.second.SecondActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by mmw on 2019/12/23.
 **/
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val mainActivity = IntentsTestRule(SplashActivity::class.java)

    @Test
    fun testInjector() {
//        val injector = Injector.Builder().build()
//        println(injector.getInstance(A::class.java).providerA("123"))
//        injector.getInstance(Plugin::class.java).test()
    }

    @Test
    fun test() {
        AndroidJUnit4::class.java.classLoader
//        onView(withId(R.id.startActivity)).perform(click())
        intended(
            hasComponent(
                ComponentName(
                    mainActivity.activity,
                    SecondActivity::class.java
                )
            )
        )
    }

}