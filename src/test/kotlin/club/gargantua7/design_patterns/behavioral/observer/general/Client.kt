package club.gargantua7.design_patterns.behavioral.observer.general

import kotlin.test.Test

/**
 * @author Gargantua丶
 **/
class Client {

    @Test
    fun test() {
        val observer = Observable<Int>()
        observer.observers += arrayOf(ObserverA(), ObserverB())
        observer.value = 666
    }
}