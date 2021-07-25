package club.gargantua7.design_patterns.graphic.adapter

import kotlin.test.Test

/**
 * @author Gargantua丶
 **/
class Client {

    @Test
    fun classAdapterTest() {
        Target(ClassAdapter())
    }

    @Test
    fun objectAdapterTest() {
        Target(ObjectAdapter(Adaptee()))
    }
}