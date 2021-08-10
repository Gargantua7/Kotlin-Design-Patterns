package club.gargantua7.design_patterns.behavioral.memento.black

/**
 * @author Gargantua丶
 **/
class Originator(var state: String) {

    fun createMemento(): AbstractMemento = Memento(state)
    fun restoreMemento(memento: AbstractMemento) {
        state = (memento as Memento).state
    }

    private data class Memento(val state: String): AbstractMemento
}