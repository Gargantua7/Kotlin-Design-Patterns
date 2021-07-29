package club.gargantua7.design_patterns.graphic.composite

/**
 * @author Gargantua丶
 **/
abstract class Component(protected val name: String, val depth: Int) {

    abstract fun operation()

    open operator fun plusAssign(component: Component) {
        throw UnsupportedOperationException()
    }

    open operator fun minusAssign(component: Component) {
        throw UnsupportedOperationException()
    }

    open operator fun get(index: Int): Component {
        throw UnsupportedOperationException()
    }
}