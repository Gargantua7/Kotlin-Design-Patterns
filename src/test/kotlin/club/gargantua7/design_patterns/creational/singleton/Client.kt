package club.gargantua7.design_patterns.creational.singleton

import org.junit.jupiter.api.Test

/**
 * @author Gargantua丶
 **/
class Client {

    @Test
    fun test() {
        println(Hungry.Instance1.instance == Hungry.Instance1.instance)
        println(Hungry.Instance2.instance == Hungry.Instance2.instance)
    }
}