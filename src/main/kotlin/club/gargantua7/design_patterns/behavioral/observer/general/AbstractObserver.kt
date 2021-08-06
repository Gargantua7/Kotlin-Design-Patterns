package club.gargantua7.design_patterns.behavioral.observer.general

interface AbstractObserver<E> {
    fun update(value: E)
}
