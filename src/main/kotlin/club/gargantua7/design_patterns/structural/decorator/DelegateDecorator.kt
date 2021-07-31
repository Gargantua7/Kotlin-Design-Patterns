package club.gargantua7.design_patterns.structural.decorator

/**
 * @author Gargantua丶
 **/
class DelegateDecorator(private val component: AbstractComponent) : AbstractComponent by component {

    override fun request() {
        println("Delegate Decorator is Requested")
        component.request()
    }
}