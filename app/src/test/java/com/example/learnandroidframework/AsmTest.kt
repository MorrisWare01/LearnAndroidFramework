package com.example.learnandroidframework

import org.junit.Test
import java.io.File

//import org.objectweb.asm.ClassReader
//import org.objectweb.asm.ClassWriter

/**
 * Created by mmw on 2019/12/27.
 **/
class AsmTest {

    @Test
    fun test() {
//        val classReader = ClassReader(Account::class.java.name)
//        val classWriter = ClassWriter(ClassWriter.COMPUTE_MAXS)
        val file = File("/text.txt")
        file.createNewFile()
        println(File("http://www.baidu.com").exists())
        println(file.absolutePath)
        println(File(file.absolutePath).exists())
    }

    class Account {
        fun onClick() {
            println("onClick")
        }
    }

    @Test
    fun test1() {

    }


}