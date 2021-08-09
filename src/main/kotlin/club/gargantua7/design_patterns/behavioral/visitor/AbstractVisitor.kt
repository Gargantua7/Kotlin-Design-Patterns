package club.gargantua7.design_patterns.behavioral.visitor

interface AbstractVisitor {

    fun visit(e: ElementA)
    fun visit(e: ElementB)
}
