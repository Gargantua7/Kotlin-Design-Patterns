package club.gargantua7.design_patterns.behavioral.observer.general

/**
 * @author Gargantua丶
 **/
abstract class AbstractObservable<E> {
    abstract val observers: MutableSet<AbstractObserver<E>>
}