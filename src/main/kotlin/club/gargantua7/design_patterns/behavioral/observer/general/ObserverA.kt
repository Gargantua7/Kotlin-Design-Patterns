package club.gargantua7.design_patterns.behavioral.observer.general

class ObserverA<E> : AbstractObserver<E> {

    override fun update(value: E) {
        println("A: Value has Changed to $value")
    }
}