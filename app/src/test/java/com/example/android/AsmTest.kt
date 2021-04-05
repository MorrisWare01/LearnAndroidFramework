package com.example.android

import com.google.gson.Gson
import org.junit.Test

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
//        val file = File("/text.txt")
//        file.createNewFile()
//        println(File("http://www.baidu.com").exists())
//        println(file.absolutePath)
//        println(File(file.absolutePath).exists())
    }

    class Account {
        fun onClick() {
            println("onClick")
        }
    }

    data class A(
        val l: String? = "100"
    )

    data class B(
        val l: Long
    )


    @Test
    fun test1() {
        val gson = Gson()
        val data = gson.fromJson<B>(gson.toJson(A()), B::class.java)
        println(data.l)

    }


}