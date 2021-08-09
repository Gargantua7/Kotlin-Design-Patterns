package club.gargantua7.design_patterns.behavioral.visitor

/**
 * @author Gargantua丶
 **/
class Structure {

    private val list = ArrayList<AbstractElement>()

    init {
        list.add(ElementA())
        list.add(ElementB())
    }

    fun accept(visitor: AbstractVisitor) {
        list.forEach { e -> e.accept(visitor) }
    }
}