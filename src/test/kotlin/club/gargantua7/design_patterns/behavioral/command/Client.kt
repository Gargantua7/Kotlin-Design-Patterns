package club.gargantua7.design_patterns.behavioral.command

import kotlin.test.Test

/**
 * @author Gargantua丶
 **/
class Client {

    @Test
    fun test() {
        Invoker(ConcreteCommand()).request()
    }
}