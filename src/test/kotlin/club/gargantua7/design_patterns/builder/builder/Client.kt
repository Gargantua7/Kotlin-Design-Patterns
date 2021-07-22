package club.gargantua7.design_patterns.builder.builder

import kotlin.test.Test

/**
 * @author Gargantua丶
 **/
class Client {
    @Test
    fun generalTest() {
        val product = GeneralBuilder().run {
            setName("P1")
            setId(1)
            setLength(50)
            setHeight(50)
            setWeight(50)
            build()
        }
        println(product)
    }

    @Test
    fun chainTest() {
        val product = ChainBuilder()
            .setName("P2")
            .setId(2)
            .setLength(50)
            .setHeight(50)
            .setWeight(50)
            .build()
        println(product)
    }

    @Test
    fun kotlinBest() {
        val product = Product(id = 3, name = "P3")
        println(product)
    }

    @Test
    fun require() {
        Product()
    }
}