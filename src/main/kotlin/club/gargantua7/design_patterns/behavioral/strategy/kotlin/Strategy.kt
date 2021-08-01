package club.gargantua7.design_patterns.behavioral.strategy.kotlin

/**
 * @author Gargantua丶
 **/
class Context(private val method: () -> Unit = { println("Requesting in Default Method") }) {

    fun request() = method()
}

val strategyA = { println("Requesting in Strategy A") }

val strategyB = { println("Requesting in Strategy B") }