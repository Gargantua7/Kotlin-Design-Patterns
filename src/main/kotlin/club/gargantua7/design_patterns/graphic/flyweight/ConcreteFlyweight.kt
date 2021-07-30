package club.gargantua7.design_patterns.graphic.flyweight

/**
 * @author Gargantua丶
 **/
class ConcreteFlyweight(val name: String) : AbstractFlyweight {

    override fun request() {
        println("$name $this")
    }
}