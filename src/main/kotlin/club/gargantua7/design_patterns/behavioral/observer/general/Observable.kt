package club.gargantua7.design_patterns.behavioral.observer.general

class Observable<E> : AbstractObservable<E>() {

    override val observers: MutableSet<AbstractObserver<E>> = HashSet()

    var value: E? = null
        set(value) {
            value?.let {
                observers.forEach { it.update(value) }
                field = value
            }
        }
}