package club.gargantua7.design_patterns.builder.factory

import kotlin.test.Test

/**
 * @author Gargantua丶
 **/
class Client {

    @Test
    fun simpleFactory() {
        val simpleFactory = SimpleFactory()
        simpleFactory(ProductType.ProductA).onCreate()
        simpleFactory(ProductType.ProductB).onCreate()
    }

    @Test
    fun simpleStaticFactoryTest() {
        SimpleStaticFactory(ProductType.ProductA).onCreate()
        SimpleStaticFactory(ProductType.ProductB).onCreate()
    }

    @Test
    fun factoryTest() {
        ProductAFactory().onCreate()
        ProductBFactory().onCreate()
    }

    @Test
    fun abstractFactoryTest() {
        FamilyAFactory.createProductA().onCreate()
        FamilyAFactory.createProductB().onCreate()
        FamilyBFactory.createProductA().onCreate()
        FamilyBFactory.createProductB().onCreate()
    }

    @Test
    fun inlineFactoryTest() {
        AbstractFactory<ProductAWithFamilyA>().createProductA().onCreate()
        AbstractFactory<ProductBWithFamilyA>().createProductB().onCreate()
        AbstractFactory<ProductAWithFamilyB>().createProductA().onCreate()
        AbstractFactory<ProductBWithFamilyB>().createProductB().onCreate()
    }
}