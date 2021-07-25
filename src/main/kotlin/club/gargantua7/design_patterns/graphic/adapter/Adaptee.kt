package club.gargantua7.design_patterns.graphic.adapter

/**
 * 源对象
 *
 * @author Gargantua丶
 **/
open class Adaptee {
    fun requestAdaptee() {
        println("Requested in Adaptee")
    }
}