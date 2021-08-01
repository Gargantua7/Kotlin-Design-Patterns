package club.gargantua7.design_patterns.behavioral.strategy.general

/**
 * @author Gargantua丶
 **/
class Context(private val strategy: AbstractStrategy) {

    fun request() = strategy.request()
}