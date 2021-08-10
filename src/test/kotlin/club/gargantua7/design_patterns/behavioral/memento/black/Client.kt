package club.gargantua7.design_patterns.behavioral.memento.black

import club.gargantua7.design_patterns.behavioral.memento.white.Caretaker
import club.gargantua7.design_patterns.behavioral.memento.white.Originator
import kotlin.test.Test

/**
 * @author Gargantua丶
 **/
class Client {

    @Test
    fun test() {
        val originator = Originator("A")
        val caretaker = Caretaker()
        caretaker[1] = originator.createMemento()
        originator.state = "B"
        caretaker[2] = originator.createMemento()
        originator.state = "C"
        originator.restoreMemento(caretaker[1]!!)
        println(originator.state)
        originator.restoreMemento(caretaker[2]!!)
        println(originator.state)
    }
}