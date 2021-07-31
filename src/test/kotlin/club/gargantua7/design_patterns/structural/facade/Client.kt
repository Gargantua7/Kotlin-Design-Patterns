package club.gargantua7.design_patterns.structural.facade

import kotlin.test.Test

/**
 * @author Gargantua丶
 **/
class Client {
    @Test
    fun test() {
        Facade.requestA()
        Facade.requestB()
        Facade.requestC()
    }
}