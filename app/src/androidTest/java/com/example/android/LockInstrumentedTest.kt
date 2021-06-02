package com.example.android

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.locks.ReentrantLock

/**
 * Created by mmw on 2021/5/6.
 */
@RunWith(AndroidJUnit4::class)
class LockInstrumentedTest {

    @Test
    fun lock() {
        val lock = ReentrantLock()
        val condition = lock.newCondition()

        Thread {
            lock.lock()
            condition.awaitNanos(1000)
            lock.unlock()
        }.start()

        Thread {
            lock.lock()
            lock.unlock()
        }.start()

        Thread {
            lock.lock()
            condition.signal()
            lock.unlock()
        }.start()

        while (Thread.activeCount() > 1) {
            Thread.yield()
        }
    }
}