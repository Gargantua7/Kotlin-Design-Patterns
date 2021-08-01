package club.gargantua7.design_patterns.behavioral.strategy.kotlin

import kotlin.test.Test

/**
 * @author Gargantua丶
 **/
class Client {

    @Test
    fun test() {
        Context().request()
        Context(strategyA).request()
        Context(strategyB).request()
    }

    @Test
    fun customizeTest() {
        Context { println("Requesting in Customize Lambda") }.request()
    }
}