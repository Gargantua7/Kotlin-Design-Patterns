package club.gargantua7.design_patterns.behavioral.strategy.general

import kotlin.test.Test

/**
 * @author Gargantua丶
 **/
class Client {

    @Test
    fun test() {
        Context(ConcreteStrategyA()).request()
        Context(ConcreteStrategyB()).request()
    }
}