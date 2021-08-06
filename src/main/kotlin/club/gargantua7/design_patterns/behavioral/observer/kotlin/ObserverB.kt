package club.gargantua7.design_patterns.behavioral.observer.kotlin

class ObserverB<E> : AbstractObserver<E> {

    override fun update(value: E) {
        println("B: Value has Changed to $value")
    }
}