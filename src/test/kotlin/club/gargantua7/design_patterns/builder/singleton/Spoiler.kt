package club.gargantua7.design_patterns.builder.singleton

import org.junit.jupiter.api.Test
import java.io.*
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

/**
 * 破坏单例模式
 *
 * @author Gargantua丶
 **/
class Spoiler {

    /**
     * 利用 I/O 及序列化破坏单例模式
     **/
    @Test
    fun ioTest() {
        println("饿汉式被击碎：${Hungry.Instance1.instance == io(Hungry.Instance1.instance)}")
        println("懒汉式也是一样：${Lazy.General.instance == io(Lazy.General.instance)}")
        println("即使Kotlin的单例模式也无法避免：${Kotlin == io(Kotlin)}")
        println("但是枚举表示小意思：${Enum.INSTANCE == io(Enum.INSTANCE)}")
        println("加入了反序列化保护方式的单例成功抵御了破坏：${SerializableProtector == io(SerializableProtector)}")
    }

    private fun <T> io(origin: T): T {
        val out = ByteArrayOutputStream()
        ObjectOutputStream(out).use { it.writeObject(origin) }
        return ObjectInputStream(ByteArrayInputStream(out.toByteArray())).use { it.readObject() } as T
    }

    /**
     * 利用 Java 反射破坏单例模式
     **/
    @Test
    fun reflectTest() {
        println("饿汉式被击碎：${Hungry.Instance1.instance == reflect<Hungry>()}")
        println("懒汉式也是一样：${Lazy.General.instance == reflect<Lazy>()}")
        println("即使Kotlin的单例模式也无法避免：${Kotlin == reflect<Kotlin>()}")
        println("但是反射无法实例化枚举类")
        println("加入了反射保护方式的单例成功抵御了破坏：${ReflectProtector.instance == reflect<ReflectProtector>()}")
    }

    private inline fun <reified T> reflect(): T {
        val clazz = T::class.java
        val constructors = clazz.getDeclaredConstructor()
        constructors.isAccessible = true
        return constructors.newInstance() as T
    }

    /**
     * 利用 Kotlin 反射破坏单例模式
     **/
    @Test
    fun reflectKTest() {
        println("饿汉式被击碎：${Hungry.Instance1.instance == reflectK<Hungry>()}")
        println("懒汉式也是一样：${Lazy.General.instance == reflectK<Lazy>()}")
        println("但是Kotlin的单例模式在Kotlin反射中无构造方法")
        println("但是反射无法实例化枚举类")
        println("加入了反射保护方式的单例成功抵御了破坏：${ReflectProtector.instance == reflectK<ReflectProtector>()}")
    }

    private inline fun <reified T> reflectK(): T {
        val clazz = T::class
        val constructor = clazz.constructors.first()
        constructor.isAccessible = true
        return constructor.call()
    }

    private inline fun <reified T> reflectK(origin: T): T {
        val new = reflectK<T>()
        println(origin == new)
        return new
    }

    /**
     * 还是用反射破坏 ReflectProtector
     * 至此已证明一般 Class 实现的单例模式无法防御反射破坏
     */
    @Test
    fun reflectX() {
        val origin = ReflectProtector.instance
        val clazz = ReflectProtector::class
        val flag = clazz.companionObject?.memberProperties?.first { it.name == "flag" }
        if (flag is KMutableProperty<*>) {
            flag.isAccessible = true
            flag.setter.call(clazz.companionObjectInstance, false)
        }

        val constructor = clazz.constructors.first()
        constructor.isAccessible = true
        val new = constructor.call()
        println(new == origin)
    }

    @Test
    fun reflect() {
        val clazz = Enum::class
        println(clazz.constructors.first().apply { isAccessible = true }.call())
    }
}