package com.example.android

import org.junit.Test
import java.util.concurrent.locks.ReentrantLock

/**
 * Created by mmw on 2021/5/6.
 */
class LockTest {

    @Test
    fun lock() {
        val lock = ReentrantLock()
        val condition = lock.newCondition()

        Thread {
            lock.lock()
            condition.await()
            lock.unlock()
        }.start()

        Thread {
            lock.lock()
            condition.await()
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