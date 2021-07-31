package club.gargantua7.design_patterns.structural.decorator

/**
 * @author Gargantua丶
 **/
class ConcreteComponent : AbstractComponent{

    override fun request() {
        println("ConcreteComponent is requested")
    }
}