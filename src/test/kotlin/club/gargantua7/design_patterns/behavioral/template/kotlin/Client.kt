package club.gargantua7.design_patterns.behavioral.template.kotlin

import kotlin.test.Test

/**
 * @author Gargantua丶
 **/
class Client {

    @Test
    fun templateTest() {
        Template().request(after = ::candidateMethodB)
    }

    @Test
    fun customizeTest() {
        Template().request(requesting = { println("Customize Requesting") })
    }
}