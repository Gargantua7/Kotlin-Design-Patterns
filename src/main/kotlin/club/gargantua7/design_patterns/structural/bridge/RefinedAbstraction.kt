package club.gargantua7.design_patterns.structural.bridge

/**
 * @author Gargantua丶
 **/
class RefinedAbstraction(abstractImplementor: AbstractImplementor) : Abstraction(abstractImplementor) {
    override fun request() {
        println("Refined Abstraction is Requested")
        super.request()
    }
}