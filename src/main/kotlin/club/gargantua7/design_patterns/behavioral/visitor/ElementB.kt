package club.gargantua7.design_patterns.behavioral.visitor

class ElementB : AbstractElement {

    override fun accept(visitor: AbstractVisitor) {
        visitor.visit(this)
    }

    fun request(): String = javaClass.simpleName
}