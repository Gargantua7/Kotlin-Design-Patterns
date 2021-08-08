package club.gargantua7.design_patterns.behavioral.iterator

class Aggregate<E> : AbstractAggregate<E>, Iterable<E> {

    private val list = ArrayList<E>()

    override fun add(element: E) {
        list.add(element)
    }

    override fun remove(element: E) {
        list.remove(element)
    }

    override operator fun iterator(): Iterator<E> = Itr()

    private inner class Itr : Iterator<E> {

        private var index = 0

        override fun next(): E = list[index++]

        override fun hasNext(): Boolean = index < list.size
    }
}