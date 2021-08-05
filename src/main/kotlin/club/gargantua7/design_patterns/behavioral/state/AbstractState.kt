package club.gargantua7.design_patterns.behavioral.state

/**
 * @author Gargantua丶
 **/
abstract class AbstractState(protected val context: Context) {

    abstract fun handler()
}