package club.gargantua7.design_patterns.behavioral.template.general

/**
 * @author Gargantua丶
 **/
abstract class AbstractTemplate {

    protected open fun before() {
        println("Before")
    }

    protected open fun requesting() {
        println("Request")
    }

    protected open fun after() {
        println("After")
    }

    fun request() {
        before()
        requesting()
        after()
    }
}