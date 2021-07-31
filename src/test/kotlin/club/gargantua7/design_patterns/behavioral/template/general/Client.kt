package club.gargantua7.design_patterns.behavioral.template.general

import kotlin.test.Test

/**
 * @author Gargantua丶
 **/
class Client {

    @Test
    fun test() {
        ConcreteTemplateA().request()
        ConcreteTemplateB().request()
    }
}