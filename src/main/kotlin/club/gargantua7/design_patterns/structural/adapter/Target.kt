package club.gargantua7.design_patterns.structural.adapter

/**
 * @author Gargantua丶
 **/
object Target {
    operator fun invoke(target: TargetInterface) {
        target.request()
    }
}