package club.gargantua7.design_patterns.creational.factory

/**
 * 简单工厂
 * @author Gargantua丶
 **/
class SimpleFactory {
    operator fun invoke(type: ProductType) =
        when (type) {
            ProductType.ProductA -> ProductA()
            ProductType.ProductB -> ProductB()
        }
}

object SimpleStaticFactory {
    operator fun invoke(type: ProductType) =
        when (type) {
            ProductType.ProductA -> ProductA()
            ProductType.ProductB -> ProductB()
        }
}