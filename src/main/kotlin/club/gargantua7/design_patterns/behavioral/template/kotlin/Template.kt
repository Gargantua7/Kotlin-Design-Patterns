package club.gargantua7.design_patterns.behavioral.template.kotlin

/**
 * @author Gargantua丶
 **/
class Template {

    private fun before() {
        println("Before")
    }

    private fun requesting() {
        println("Request")
    }

    private fun after() {
        println("After")
    }

    fun request(
        before: () -> Unit = ::before,
        requesting: () -> Unit = ::requesting,
        after: () -> Unit = ::after,
    ) {
        before()
        requesting()
        after()
    }
}

fun candidateMethodA() {
    println("In Candidate A")
}

fun candidateMethodB() {
    println("In Candidate B")
}

fun candidateMethodC() {
    println("In Candidate B")
}