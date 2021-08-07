package club.gargantua7.design_patterns.behavioral.mediator

/**
 * @author Gargantua丶
 **/
abstract class AbstractMediator {

    protected val colleagueA by lazy {  ColleagueA(this) }
    protected val colleagueB by lazy {  ColleagueB(this) }

    abstract fun transferA()
    abstract fun transferB()
}