package club.gargantua7.design_patterns.graphic.proxy

import kotlin.test.Test

/**
 * @author Gargantua丶
 **/
class Client {

    @Test
    fun staticProxyTest() {
        StaticProxy.Proxy(StaticProxy.RealSubject()).request()
    }

    @Test
    fun jdkProxyTest() {
        JDKProxy.JDKProxy(JDKProxy.RealSubject()).request()
    }

    @Test
    fun cglibProxyTest() {
        CGLIBProxy.CGLIBProxy(CGLIBProxy.RealSubject()).request()
    }

    @Test
    fun kotlinDelegate() {
        KotlinDelegate.KotlinDelegate(KotlinDelegate.RealSubject()).clearRequest()
    }
}