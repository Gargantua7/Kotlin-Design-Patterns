package club.gargantua7.design_patterns.structural.proxy

import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * JDK 动态代理
 *
 * @author Gargantua丶
 **/
object JDKProxy {
    interface Subject {
        fun request()
    }

    class RealSubject : Subject {
        override fun request() {
            println("Real Subject is requested")
        }
    }

    object JDKProxy {
        operator fun invoke(subject: Subject) = Proxy.newProxyInstance(
            subject.javaClass.classLoader,
            subject.javaClass.interfaces,
        ) { _: Any, method: Method, _: Array<Any>? ->
            before()
            println("JDK Proxy is working")
            val res = method.invoke(subject)
            after()
            return@newProxyInstance res
        } as Subject

        private fun before() {
            println("called before request")
        }

        private fun after() {
            println("called after request")
        }
    }
}

//fun main() {
//    System.getProperties()["sun.misc.ProxyGenerator.saveGeneratedFiles"] = "true"
//    JDKProxy.JDKProxy(JDKProxy.RealSubject()).apply {
//        println(this)
//        request()
//    }
//}