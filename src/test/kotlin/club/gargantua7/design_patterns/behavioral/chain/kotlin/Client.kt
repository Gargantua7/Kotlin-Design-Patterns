package club.gargantua7.design_patterns.behavioral.chain.kotlin

import kotlin.test.Test

/**
 * @author Gargantua丶
 **/
class Client {

    @Test
    fun test() {
        val chain = HandlerA to HandlerB
        chain("A")
        chain("B")
    }
}