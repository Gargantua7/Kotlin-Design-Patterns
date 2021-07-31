package club.gargantua7.design_patterns.creational.factory

/**
 * 抽象工厂模式
 * @author Gargantua丶
 **/

interface AbstractProductA : AbstractProduct
interface AbstractProductB : AbstractProduct

class ProductAWithFamilyA : AbstractProductA
class ProductBWithFamilyA : AbstractProductB

class ProductAWithFamilyB : AbstractProductA
class ProductBWithFamilyB : AbstractProductB

abstract class AbstractFactory {
    abstract fun createProductA(): AbstractProductA
    abstract fun createProductB(): AbstractProductB

    companion object {
        /** 内联方法简化 */
        inline operator fun <reified T : AbstractProduct> invoke(): AbstractFactory {
            return when(T::class) {
                ProductAWithFamilyA::class, ProductBWithFamilyA::class -> FamilyAFactory
                ProductAWithFamilyB::class, ProductBWithFamilyB::class -> FamilyBFactory
                else -> throw IllegalArgumentException()
            }
        }
    }
}

object FamilyAFactory : AbstractFactory() {
    override fun createProductA(): AbstractProductA = ProductAWithFamilyA()

    override fun createProductB(): AbstractProductB = ProductBWithFamilyA()
}

object FamilyBFactory : AbstractFactory() {
    override fun createProductA(): AbstractProductA = ProductAWithFamilyB()

    override fun createProductB(): AbstractProductB = ProductBWithFamilyB()
}