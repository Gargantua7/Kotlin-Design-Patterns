package club.gargantua7.design_patterns.builder.prototype

/**
 * 浅克隆
 * @author Gargantua丶
 **/
class Shallow: Cloneable {

    val flag = Flag()

    public override fun clone(): Shallow {
        return super.clone() as Shallow
    }
}