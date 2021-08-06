package club.gargantua7.design_patterns.behavioral.observer.kotlin

import kotlin.test.Test

/**
 * @author Gargantua丶
 **/
class Client {
    @Test
    fun observablePropertyTest() {
        Observable().valueByObservableProperty = 666
    }

    @Test
    fun vetoesTest() {
        val observable = Observable()
        observable.valueByVetoes = 666
        println(observable.valueByVetoes)
        observable.valueByVetoes = -1
        println(observable.valueByVetoes)
    }

    @Test
    fun observableTest() {
        Observable().valueByObserver = 666
    }
}