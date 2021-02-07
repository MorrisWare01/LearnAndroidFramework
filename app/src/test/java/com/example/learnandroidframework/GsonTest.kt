package com.example.learnandroidframework

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Since
import com.google.gson.annotations.Until
import com.google.gson.reflect.TypeToken
import org.junit.Test
import java.lang.IllegalArgumentException
import java.lang.reflect.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

/**
 * Created by mmw on 2021/2/6.
 */
class GsonTest {

    val gson = GsonBuilder()
        .setVersion(1.0)
        .addSerializationExclusionStrategy(object : ExclusionStrategy {
            override fun shouldSkipClass(clazz: Class<*>?): Boolean {
//                if (clazz?.isAssignableFrom(Bean::class.java) == true) {
//                    return true
//                }
                return false
            }

            override fun shouldSkipField(f: FieldAttributes?): Boolean {
//                if (f?.declaringClass?.isAssignableFrom(Bean::class.java) == true) {
//                    return true
//                }
                return false
            }
        })
        .create()

    @Test
    fun test() {
//        println(gson.fromJson("1", Int::class.java))
        println(gson.fromJson(".1", Float::class.java))
        println(gson.fromJson("{\"i\": 1}", Bean::class.java))
        val data = "true"
        assert(gson.fromJson<Boolean>(data, Boolean::class.java))
        println(gson.fromJson<List<String>>("[]", object : TypeToken<List<String>>() {}.type))
        println(
            gson.fromJson<Map<String, String>>(
                "[[\"A\", \"B\"], [\"C\",\"C\"]]",
                object : TypeToken<HashMap<String, String>>() {}.type
            )
        )

        println(gson.toJson(Bean("Ware")))
        println(gson.toJson(EnumBean.BEAN))
        print(gson.toJson(arrayOf(listOf<List<String>>(), listOf(
            ArrayList<String>().apply { add("1"); add("2") },
            ArrayList<String>().apply { add("3") }
        ))))
    }

    @Test
    fun testType() {
        assert(Any::class.java.isAssignableFrom(List::class.java))
        assert(!List::class.java.isAssignableFrom(Any::class.java))


        val stringArrayList = getCollection(object : ParameterizedType {
            override fun getRawType(): Type {
                return List::class.java
            }

            override fun getOwnerType(): Type? {
                return null
            }

            override fun getActualTypeArguments(): Array<Type> {
                return arrayOf(String::class.java)
            }
        })
        assert(stringArrayList is ArrayList)
        assert(getCollection(Set::class.java) is HashSet)

    }

    private fun getCollection(type: Type): Collection<*>? {
        val rawType = getRawType(type)
        if (!Collection::class.java.isAssignableFrom(rawType)) {
            return null
        }

        getElementType(type, rawType, Collection::class.java)

        return when {
            Set::class.java.isAssignableFrom(rawType) -> {
                HashSet<Any>()
            }
            Queue::class.java.isAssignableFrom(rawType) -> {
                ArrayDeque<Any>()
            }
            else -> {
                ArrayList<Any>()
            }
        }
    }

    private fun getRawType(type: Type): Class<*> {
        return when (type) {
            is Class<*> -> type
            is ParameterizedType -> type.rawType as Class<*>
            is WildcardType -> type.upperBounds[0] as Class<*>
            is GenericArrayType -> java.lang.reflect.Array.newInstance(
                getRawType(type.genericComponentType),
                0
            ).javaClass
            is TypeVariable<*> -> Any::class.java
            else -> throw IllegalArgumentException()
        }
    }

    private fun getElementType(type: Type, rawType: Class<*>, superType: Class<*>): Class<*> {
        assert(superType::class.java.isAssignableFrom(rawType))
        throw IllegalArgumentException()
    }

    class Bean(
        @Since(0.0)
        @Until(1.1)
        val name: String?,
        val uuid: UUID = UUID.fromString("acd257f8-a0fb-4626-aab0-552ddd2217e6"),
        var i: Int = 0,
        var ii: Int? = 10
    ) {
        init {
            println("construct")
        }
    }

    data class SerializedBean(
        var name: Boolean
    )

    enum class EnumBean {
        BEAN
    }

}