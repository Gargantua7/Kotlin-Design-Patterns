package club.gargantua7.design_patterns.structural.composite

/**
 * @author Gargantua丶
 **/
class Left(name: String, depth: Int) : Component(name, depth) {

    override fun operation() {
        println("${ buildString { for (i in 1..depth) append("  ")}}$name")
    }
}