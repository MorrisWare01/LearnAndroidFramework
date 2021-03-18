package com.example.learnandroidframework

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by mmw on 2019/11/12.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {

    @get:Rule
    val mainActivityRule = ActivityTestRule(SplashActivity::class.java)

    @Test
    fun test() {
//        onView(withId(R.id.startActivity)).check(matches(ViewMatchers.isClickable()))
//        onView(withId(R.id.startActivity)).perform(click())
    }

}