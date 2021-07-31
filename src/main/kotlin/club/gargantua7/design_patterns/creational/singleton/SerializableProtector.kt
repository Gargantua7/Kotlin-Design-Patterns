package club.gargantua7.design_patterns.creational.singleton

import java.io.Serializable

/**
 * 在反序列化时直接返回单例以避免序列化方式破坏
 *
 * @author Gargantua丶
 **/
object SerializableProtector : Serializable {

    private fun readResolve(): Any = SerializableProtector
}