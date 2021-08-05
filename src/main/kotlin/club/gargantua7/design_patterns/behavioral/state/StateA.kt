package club.gargantua7.design_patterns.behavioral.state

/**
 * @author Gargantua丶
 **/
class StateA(context: Context) : AbstractState(context) {

    override fun handler() {
        println("A is Handling the Request")
        context.currState = context.stateB
    }
}