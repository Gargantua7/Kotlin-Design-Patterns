package club.gargantua7.design_patterns.behavioral.observer.kotlin

import kotlin.properties.Delegates
import kotlin.properties.ObservableProperty
import kotlin.reflect.KProperty

/**
 * @author Gargantua丶
 **/
class Observable {

    private val observers = setOf<AbstractObserver<Int>>(ObserverA(), ObserverB())

    var valueByObservableProperty by object : ObservableProperty<Int>(0) {
        override fun beforeChange(property: KProperty<*>, oldValue: Int, newValue: Int): Boolean = newValue > 0

        override fun afterChange(property: KProperty<*>, oldValue: Int, newValue: Int) {
            observers.forEach { it.update(newValue) }
        }
    }

    var valueByVetoes by Delegates.vetoable(0) { _, _, newValue ->
        newValue > 0
    }

    var valueByObserver by Delegates.observable(0) { _, _, newValue ->
        observers.forEach { it.update(newValue) }
    }
}