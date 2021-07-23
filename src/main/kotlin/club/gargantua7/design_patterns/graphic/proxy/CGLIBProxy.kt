package club.gargantua7.design_patterns.graphic.proxy

import net.sf.cglib.proxy.Enhancer
import net.sf.cglib.proxy.MethodInterceptor
import net.sf.cglib.proxy.MethodProxy
import java.lang.reflect.Method

/**
 * CGLIB 动态代理
 * 相比 JDK 代理的好处是不需要接口
 *
 * @author Gargantua丶
 **/
object CGLIBProxy {

    // 类和方法都要设置为可继承
    open class RealSubject {
        open fun request() {
            println("Real Subject is requesting")
        }
    }

    object CGLIBProxy {
        operator fun invoke(subject: RealSubject): RealSubject {
            val enhancer = Enhancer()
            // 设置代理类父类
            enhancer.setSuperclass(RealSubject::class.java)
            // 设置回调
            enhancer.setCallback(
                MethodInterceptor { _: Any, method: Method, _: Array<Any>, _: MethodProxy ->
                    before()
                    println("CGLIB Proxy is working")
                    val res = method.invoke(subject)
                    after()
                    return@MethodInterceptor res
            })
            // 创建代理类并返回
            return enhancer.create() as RealSubject
        }

        private fun before() {
            println("called before request")
        }
        private fun after() {
            println("called after request")
        }
    }
}