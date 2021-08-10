package club.gargantua7.design_patterns.behavioral.memento.black

/**
 * @author Gargantua丶
 **/
class Caretaker {

    private val map = HashMap<Int, AbstractMemento>()

    operator fun get(version: Int) = map[version]

    operator fun set(version: Int, abstractMemento: AbstractMemento) {
        map[version] = abstractMemento
    }
}