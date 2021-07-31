package club.gargantua7.design_patterns.structural.flyweight

/**
 * @author Gargantua丶
 **/
object FlyweightFactory {

    private val pool = HashMap<String, AbstractFlyweight>()

    operator fun get(name: String): AbstractFlyweight {
        if (name !in pool) pool[name] = ConcreteFlyweight(name)
        return pool[name]!!
    }
}