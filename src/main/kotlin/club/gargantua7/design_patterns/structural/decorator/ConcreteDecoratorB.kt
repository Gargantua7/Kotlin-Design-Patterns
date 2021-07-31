package club.gargantua7.design_patterns.structural.decorator

/**
 * @author Gargantua丶
 **/
class ConcreteDecoratorB(override val component: AbstractComponent) : Decorator(component) {

    override fun request() {
        println("ConcreteDecorator B is Requested")
        super.request()
    }
}