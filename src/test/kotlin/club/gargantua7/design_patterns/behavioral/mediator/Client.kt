package club.gargantua7.design_patterns.behavioral.mediator

import kotlin.test.Test

/**
 * @author Gargantua丶
 **/
class Client {

    @Test
    fun test() {
        val mediator = Mediator()
        ColleagueA(mediator).dependent()
        ColleagueB(mediator).dependent()
    }
}