package club.gargantua7.design_patterns.creational.factory

/**
 * 产品
 * @author Gargantua丶
 **/
interface AbstractProduct {
    fun onCreate() {
        println("$this onCreate")
    }
}

class ProductA: AbstractProduct

class ProductB: AbstractProduct

enum class ProductType {
    ProductA,
    ProductB
}