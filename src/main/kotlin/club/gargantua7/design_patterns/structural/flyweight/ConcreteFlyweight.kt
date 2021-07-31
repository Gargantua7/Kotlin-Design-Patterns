package club.gargantua7.design_patterns.structural.flyweight

/**
 * @author Gargantua丶
 **/
class ConcreteFlyweight(val name: String) : AbstractFlyweight {

    override fun request() {
        println("$name $this")
    }
}