package club.gargantua7.design_patterns.behavioral.chain.general

/**
 * @author Gargantua丶
 **/
class HandlerA(nextHandler: AbstractHandler? = null) : AbstractHandler(nextHandler) {

    override fun check(target: String): Boolean = target == "A"

    override fun handler() {
        println("Requesting in $this")
    }
}