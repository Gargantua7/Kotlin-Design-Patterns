package club.gargantua7.design_patterns.behavioral.state

/**
 * @author Gargantua丶
 **/
class StateB(context: Context) : AbstractState(context) {

    override fun handler() {
        println("B is Handling the Request")
        context.currState = context.stateA
    }
}