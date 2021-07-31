package club.gargantua7.design_patterns.structural.adapter

/**
 * 类适配器
 *
 * @author Gargantua丶
 **/
class ClassAdapter: Adaptee(), TargetInterface {
    override fun request() {
        println("Requested in Adapter")
        super.requestAdaptee()
    }
}