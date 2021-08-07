package club.gargantua7.design_patterns.behavioral.mediator

/**
 * @author Gargantua丶
 **/
abstract class AbstractColleague(val mediator: AbstractMediator) {
    abstract fun self()
    abstract fun dependent()
}