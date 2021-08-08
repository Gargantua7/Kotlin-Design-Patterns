package club.gargantua7.design_patterns.behavioral.iterator

/**
 * @author Gargantua丶
 **/
interface AbstractAggregate<E> {

    fun add(element: E)

    fun remove(element: E)

    operator fun iterator(): Iterator<E>
}