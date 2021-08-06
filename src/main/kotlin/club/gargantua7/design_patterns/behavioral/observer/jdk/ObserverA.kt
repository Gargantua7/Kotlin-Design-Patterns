package club.gargantua7.design_patterns.behavioral.observer.jdk

import java.util.*
import java.util.Observable

/**
 * @author Gargantua丶
 **/
class ObserverA : Observer {

    override fun update(o: Observable?, arg: Any?) {
        println("A: Value has Changed to $arg")
    }
}