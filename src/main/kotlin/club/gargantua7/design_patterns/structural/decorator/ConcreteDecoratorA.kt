package club.gargantua7.design_patterns.structural.decorator

/**
 * @author Gargantua丶
 **/
class ConcreteDecoratorA(override val component: AbstractComponent) : Decorator(component) {

    override fun request() {
        println("ConcreteDecorator A is Requested")
        super.request()
    }
}