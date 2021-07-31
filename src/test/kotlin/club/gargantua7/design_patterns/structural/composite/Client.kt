package club.gargantua7.design_patterns.structural.composite

import kotlin.test.Test

/**
 * @author Gargantua丶
 **/
class Client {
    @Test
    fun test() {
        val root = Composite("root", 0)
        val composite1d1 = Composite("Composite 1-1", 1)
        val composite1d2 = Composite("Composite 1-2", 1)
        root += composite1d1
        root += composite1d2
        composite1d1 += Left("Left 2-1", 2)
        root.operation()
    }
}