package club.gargantua7.design_patterns.behavioral.memento.white

/**
 * @author Gargantua丶
 **/
class Originator(var state: String) {

    fun createMemento(): Memento = Memento(state)
    fun restoreMemento(memento: Memento) {
        state = memento.state
    }
}