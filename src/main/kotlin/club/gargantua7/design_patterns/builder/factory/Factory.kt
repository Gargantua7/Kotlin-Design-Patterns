package club.gargantua7.design_patterns.builder.factory

/**
 * 工厂模式
 * @author Gargantua丶
 **/
private interface AbstractFactoryMethod {
    operator fun invoke(): AbstractProduct
}

object ProductAFactory: AbstractFactoryMethod {
    override operator fun invoke() = ProductA()
}

object ProductBFactory: AbstractFactoryMethod {
    override operator fun invoke() = ProductB()
}