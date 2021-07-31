package club.gargantua7.design_patterns.structural.facade

/**
 * @author Gargantua丶
 **/
object Facade {

    private val classA = ClassA()
    private val classB = ClassB()
    private val classC = ClassC()

    fun requestA() {
        classA.request()
    }
    fun requestB() {
        classB.request()
    }
    fun requestC() {
        classC.request()
    }
}