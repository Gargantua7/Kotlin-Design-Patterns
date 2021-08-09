package club.gargantua7.design_patterns.behavioral.visitor

import kotlin.test.Test

/**
 * @author Gargantua丶
 **/
class Client {

    @Test
    fun test() {
        val structure = Structure()
        structure.accept(VisitorA())
        structure.accept(VisitorB())
    }
}