package club.gargantua7.design_patterns.graphic.bridge

import kotlin.test.Test

/**
 * @author Gargantua丶
 **/
class Client {

    @Test
    fun testA() {
        RefinedAbstraction(ImplementorA()).request()
    }

    @Test
    fun testB() {
        RefinedAbstraction(ImplementorB()).request()
    }
}