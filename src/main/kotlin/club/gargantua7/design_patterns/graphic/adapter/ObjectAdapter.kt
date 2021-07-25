package club.gargantua7.design_patterns.graphic.adapter

/**
 * 对象适配器
 *
 * @author Gargantua丶
 **/
class ObjectAdapter(private val adaptee: Adaptee): TargetInterface {
    override fun request() {
        println("Requested in Adapter")
        adaptee.requestAdaptee()
    }
}