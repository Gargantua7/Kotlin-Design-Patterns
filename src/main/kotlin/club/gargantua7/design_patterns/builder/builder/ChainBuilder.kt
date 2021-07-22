package club.gargantua7.design_patterns.builder.builder

/**
 * @author Gargantua丶
 **/
class ChainBuilder : Builder {
    private val product = Product()

    fun setName(name: String): ChainBuilder {
        product.name = name
        return this
    }

    fun setId(id: Int): ChainBuilder {
        product.id = id
        return this
    }

    fun setLength(length: Int): ChainBuilder {
        product.length = length
        return this
    }

    fun setHeight(height: Int): ChainBuilder {
        product.height = height
        return this
    }

    fun setWeight(weight: Int): ChainBuilder {
        product.weight = weight
        return this
    }

    override fun build() = product.apply {
        if(name == null && id == null)
            throw IllegalArgumentException("id and name are not allowed to be null at the same time")
    }
}