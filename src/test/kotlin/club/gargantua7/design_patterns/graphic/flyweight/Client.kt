package club.gargantua7.design_patterns.graphic.flyweight

import kotlin.test.Test

/**
 * @author Gargantua丶
 **/
class Client {

    @Test
    fun test() {
        val a = FlyweightFactory["A"]
        val b = FlyweightFactory["B"]
        a.request()
        b.request()
    }
}