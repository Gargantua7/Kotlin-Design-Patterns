package club.gargantua7.design_patterns.behavioral.chain.general

/**
 * @author Gargantua丶
 **/
abstract class AbstractHandler(private val nextHandler: AbstractHandler?) {

    fun request(target: String) {
        if (check(target)) {
            handler()
        } else {
            println("Unsupported Request in $this")
            nextHandler?.request(target) ?: throw IllegalArgumentException("$target isn't supported")
        }
    }

    abstract fun check(target: String): Boolean

    abstract fun handler()
}