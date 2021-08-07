package club.gargantua7.design_patterns.behavioral.mediator

/**
 * @author Gargantua丶
 **/
class Mediator : AbstractMediator() {

    override fun transferA() {
        colleagueB.self()
    }

    override fun transferB() {
        colleagueA.self()
    }
}