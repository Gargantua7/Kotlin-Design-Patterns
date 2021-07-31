package club.gargantua7.design_patterns.structural.proxy

/**
 * Kotlin 委托
 * 实际上是静态代理
 *
 * @author Gargantua丶
 **/
object KotlinDelegate {

    interface Subject {
        fun request()
        fun clearRequest()
    }

    class RealSubject : Subject {
        override fun request() {
            println("Real Subject is requesting")
        }

        override fun clearRequest() {
            println("Real Subject is requesting")
        }
    }

    class KotlinDelegate(private val subject: Subject) : Subject by subject {

        override fun request() {
            before()
            subject.request()
            after()
        }

        private fun before() {
            println("called before request")
        }
        private fun after() {
            println("called after request")
        }
    }
}