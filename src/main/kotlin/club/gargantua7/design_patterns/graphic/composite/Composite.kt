package club.gargantua7.design_patterns.graphic.composite

/**
 * @author Gargantua丶
 **/
class Composite(name: String, depth: Int) : Component(name, depth) {

    private val components = ArrayList<Component>()

    override fun operation() {
        println("${ buildString { for (i in 1..depth) append("  ")}}$name")
        components.forEach { component -> component.operation() }
    }

    override fun plusAssign(component: Component) {
        components += component
    }

    override fun minusAssign(component: Component) {
        components -= component
    }

    override fun get(index: Int): Component {
        return components[index]
    }
}