package club.gargantua7.design_patterns.creational.singleton

import java.io.Serializable

/**
 * 设计模式 - 单例模式 - 懒汉式
 *
 * 在类第一次被调用时才创建实例
 *
 * @author Gargantua丶
 **/
class Lazy private constructor(): Serializable {

    /**
     * 一般实现
     *
     * ！！ 线程不安全 ！！
     */
    object General {

        var instance: Lazy? = null
            private set
            get() {
                field ?: run { instance = Lazy() }
                return field
            }
    }

    /**
     * 一般线程安全实现
     *
     * 但由于每次调用都加锁，性能较差
     */
    object ThreadSafe {

        var instance: Lazy? = null
            private set
            // Synchronize 注解即 Java 中 Synchronize 关键字，但仅限修饰方法
            @Synchronized get() {
                field ?: run { instance = Lazy() }
                return field
            }


    }

    /**
     * 双重检查锁模式实现
     *
     * 解决一般安全模式性能问题
     */
    object DoubleCheck {

        // volatile 避免 JVM 进行优化重排导致返回空指针
        @Volatile
        var instance: Lazy? = null
            private set
            get() {
                field ?: run {
                    synchronized(Lazy::class.java) {
                        field ?: run { field = Lazy() }
                    }
                }
                return field
            }
    }


    /**
     * 静态内部类实现
     *
     * 线程安全且性能较好
     */
    object StaticInnerClass {

        object Inner {
            val instance = Lazy()
        }

        val instance: Lazy
            get() = Inner.instance
    }

    /**
     * Kotlin 最佳实现
     */
    object Kotlin {
        val instance by lazy { Lazy() }
    }
}