package club.gargantua7.design_patterns.behavioral.iterator

import kotlin.test.Test

/**
 * @author Gargantua丶
 **/
class Client {

    @Test
    fun test() {
        val list = Aggregate<Int>()
        list.add(1)
        list.add(2)
        for (i in list) print("$i ")
        println()
        list.forEach { print("$it ") }
        println()
        println(list.fold(0) { sum, it -> sum + it })
    }
}