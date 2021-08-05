package club.gargantua7.design_patterns.behavioral.state

import kotlin.test.Test

/**
 * @author Gargantua丶
 **/
class Client {

    @Test
    fun test() {
        val context = Context()
        context.handler()
        context.handler()
        context.handler()
    }
}