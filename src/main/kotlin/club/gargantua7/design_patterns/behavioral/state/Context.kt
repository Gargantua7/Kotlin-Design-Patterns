package club.gargantua7.design_patterns.behavioral.state

/**
 * @author Gargantua丶
 **/
class Context {

    val stateA = StateA(this)
    val stateB = StateB(this)

    var currState: AbstractState = stateA

    fun handler() = currState.handler()
}