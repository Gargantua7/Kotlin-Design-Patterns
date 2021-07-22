package club.gargantua7.design_patterns.builder.builder

/**
 * @author Gargantua丶
 **/
class GeneralBuilder : Builder {
    private val product = Product()

    fun setName(name: String) {
        product.name = name
    }

    fun setId(id: Int) {
        product.id = id
    }

    fun setLength(length: Int) {
        product.length = length
    }

    fun setHeight(height: Int) {
        product.height = height
    }

    fun setWeight(weight: Int) {
        product.weight = weight
    }

    override fun build() = product.apply {
        if(name == null && id == null)
            throw IllegalArgumentException("id and name are not allowed to be null at the same time")
    }
}