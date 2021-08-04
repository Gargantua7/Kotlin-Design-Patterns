package club.gargantua7.design_patterns.behavioral.chain.general

/**
 * @author Gargantua丶
 **/
class HandlerB(nextHandler: AbstractHandler? = null) : AbstractHandler(nextHandler) {

    override fun check(target: String): Boolean = target == "B"

    override fun handler() {
        println("Requesting in $this")
    }
}