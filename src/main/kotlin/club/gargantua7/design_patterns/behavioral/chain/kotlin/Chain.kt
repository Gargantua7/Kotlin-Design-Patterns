package club.gargantua7.design_patterns.behavioral.chain.kotlin

/**
 * @author Gargantua丶
 **/
class Chain(val check: (String) -> Boolean, private val handler: (String) -> Unit) : (String) -> Unit {

    override fun invoke(target: String) {
        return if (check(target)) handler(target) else throw IllegalArgumentException("$target isn't supported")
    }
}

infix fun Chain.to(that: Chain) = Chain({ check(it) || that.check(it) }) {
    if (check(it)) this(it) else that(it)
}

val HandlerA = Chain({ it == "A" }) { println("Requesting in A") }

val HandlerB = Chain({ it == "B" }) { println("Requesting in B") }