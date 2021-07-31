package club.gargantua7.design_patterns.structural.decorator

import kotlin.test.Test

/**
 * @author Gargantua丶
 **/
class Client {

    @Test
    fun decoratorTest() {
        val concreteComponent = ConcreteComponent()
        ConcreteDecoratorA(concreteComponent).request()
        ConcreteDecoratorB(concreteComponent).request()
    }

    @Test
    fun multiLayerTest() {
        ConcreteDecoratorA(ConcreteDecoratorB(ConcreteComponent())).request()
    }

    @Test
    fun delegateDecorator() {
        DelegateDecorator(ConcreteComponent()).request()
    }
}