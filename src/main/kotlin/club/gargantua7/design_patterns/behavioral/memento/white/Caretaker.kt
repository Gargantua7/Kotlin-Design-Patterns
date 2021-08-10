package club.gargantua7.design_patterns.behavioral.memento.white

/**
 * @author Gargantua丶
 **/
class Caretaker {

    private val map = HashMap<Int, Memento>()

    operator fun get(version: Int) = map[version]

    operator fun set(version: Int, memento: Memento) {
        map[version] = memento
    }
}