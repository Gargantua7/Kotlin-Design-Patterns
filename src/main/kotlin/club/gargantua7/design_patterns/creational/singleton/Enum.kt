package club.gargantua7.design_patterns.creational.singleton

import java.io.Serializable

/**
 * 设计模式 - 单例模式 - 枚举方式
 *
 * 本质还是饿汉式
 *
 * @author Gargantua丶
 **/
enum class Enum: Serializable {
    INSTANCE
}