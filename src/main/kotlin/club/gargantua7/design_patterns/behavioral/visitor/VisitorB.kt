package club.gargantua7.design_patterns.behavioral.visitor

class VisitorB : AbstractVisitor {

    override fun visit(e: ElementA) {
        println("result form ${javaClass.simpleName} : ${e.request()}")
    }

    override fun visit(e: ElementB) {
        println("result form ${javaClass.simpleName} : ${e.request()}")
    }
}