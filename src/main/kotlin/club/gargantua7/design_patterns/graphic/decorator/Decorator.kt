package club.gargantua7.design_patterns.graphic.decorator

/**
 * @author Gargantua丶
 **/
abstract class Decorator(protected open val component: AbstractComponent) : AbstractComponent {

    override fun request() {
        component.request()
    }
}