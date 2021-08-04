package club.gargantua7.design_patterns.behavioral.chain.general

import kotlin.test.Test

/**
 * @author Gargantua丶
 **/
class Client {

    @Test
    fun test() {
        val handler = HandlerA(HandlerB())
        handler.request("A")
        handler.request("B")
    }
}