package club.gargantua7.design_patterns.behavioral.observer.kotlin

interface AbstractObserver<E> {
    fun update(value: E)
}
