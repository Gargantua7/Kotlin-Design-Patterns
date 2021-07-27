package club.gargantua7.design_patterns.graphic.bridge

/**
 * @author Gargantua丶
 **/
class ImplementorA : AbstractImplementor {
    override fun request() {
        println("Implementor A is Requested")
    }
}