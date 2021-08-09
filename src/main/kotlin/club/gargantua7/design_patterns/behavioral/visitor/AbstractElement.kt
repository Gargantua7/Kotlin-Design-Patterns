package club.gargantua7.design_patterns.behavioral.visitor

/**
 * @author Gargantua丶
 **/
interface AbstractElement {

    fun accept(visitor: AbstractVisitor)
}