package club.gargantua7.design_patterns.builder.prototype

/**
 * @author Gargantua丶
 **/
class General(val flag: Flag): Clone<General> {

    override fun clone(): General {
        return General(flag)
    }
}

interface Clone<T> {
    fun clone(): T
}