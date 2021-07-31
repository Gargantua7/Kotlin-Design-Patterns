package club.gargantua7.design_patterns.structural.bridge

/**
 * @author Gargantua丶
 **/
abstract class Abstraction(protected open val abstractImplementor: AbstractImplementor) {
    open fun request() {
        abstractImplementor.request()
    }
}