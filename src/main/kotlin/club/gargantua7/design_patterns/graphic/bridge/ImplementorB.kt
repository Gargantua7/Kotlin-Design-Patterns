package club.gargantua7.design_patterns.graphic.bridge

/**
 * @author Gargantua丶
 **/
class ImplementorB : AbstractImplementor {
    override fun request() {
        println("Implementor B is Requested")
    }
}