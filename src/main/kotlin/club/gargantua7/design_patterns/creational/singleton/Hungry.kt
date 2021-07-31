package club.gargantua7.design_patterns.creational.singleton

import java.io.Serializable

/**
 * 设计模式 - 单例模式 - 饿汉式
 *
 * 在类被加载时就已经创建好了实例
 *
 * @author Gargantua丶
 **/
class Hungry private constructor() : Serializable {

    /**
     * 一般实现
     */
    object Instance1 {

        val instance = Hungry()
    }

    /**
     * 静态代码块实现
     */
    object Instance2 {

        var instance: Hungry
            private set

        init {
            instance = Hungry()
        }
    }

}