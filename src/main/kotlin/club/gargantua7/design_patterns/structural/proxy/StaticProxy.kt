package club.gargantua7.design_patterns.structural.proxy

/**
 * 静态代理
 *
 * @author Gargantua丶
 **/
object StaticProxy {
    interface Subject {
        fun request()
    }

    class RealSubject : Subject {
        override fun request() {
            println("Real Subject is requesting")
        }
    }

    class Proxy(private val subject: Subject) : Subject {

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