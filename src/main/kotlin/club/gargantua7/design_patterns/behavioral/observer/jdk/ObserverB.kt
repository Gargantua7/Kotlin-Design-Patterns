package club.gargantua7.design_patterns.behavioral.observer.jdk

import java.util.*
import java.util.Observable

/**
 * @author Gargantua丶
 **/
class ObserverB : Observer {

    override fun update(o: Observable?, arg: Any?) {
        println("B: Value has Changed to $arg")
    }
}