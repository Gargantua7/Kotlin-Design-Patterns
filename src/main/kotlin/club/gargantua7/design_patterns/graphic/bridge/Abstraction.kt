package club.gargantua7.design_patterns.graphic.bridge

/**
 * @author Gargantua丶
 **/
abstract class Abstraction(protected open val abstractImplementor: AbstractImplementor) {
    open fun request() {
        abstractImplementor.request()
    }
}