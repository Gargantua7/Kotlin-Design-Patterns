package club.gargantua7.design_patterns.behavioral.observer.jdk

/**
 * @author Gargantua丶
 **/
class Observable : java.util.Observable() {

    init {
        addObserver(ObserverA())
        addObserver(ObserverB())
    }

    var value: Int = 0
        set(value) {
            field = value
            setChanged()
            notifyObservers(value)
        }
}