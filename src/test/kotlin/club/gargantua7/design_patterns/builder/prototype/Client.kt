package club.gargantua7.design_patterns.builder.prototype

import kotlin.test.Test

/**
 * @author Gargantua丶
 **/
class Client {

    @Test
    fun generalCopyTest() {
        val flag = Flag()
        val origin = General(flag)
        val new = origin.clone()
        println(origin == new)
        println(origin.flag == new.flag)
    }

    @Test
    fun shallowCopyTest() {
        val origin = Shallow()
        val clone = origin.clone()
        println(origin == clone)
        println(origin.flag == clone.flag)
    }

    @Test
    fun deepCopyTest() {
        val origin = Deep()
        val clone = origin.clone()
        println(origin == clone)
        println(origin.flag == clone.flag)
    }
}