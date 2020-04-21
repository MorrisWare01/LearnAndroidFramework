package com.example.learnandroidframework

import org.junit.Test
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

/**
 * Created by mmw on 2019/12/27.
 **/
class AsmTest {

    @Test
    fun test() {
        val classReader = ClassReader(Account::class.java.name)
        val classWriter = ClassWriter(ClassWriter.COMPUTE_MAXS)
    }

    class Account {
        fun onClick() {
            println("onClick")
        }
    }









}