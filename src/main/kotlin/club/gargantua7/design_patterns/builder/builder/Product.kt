package club.gargantua7.design_patterns.builder.builder

/**
 * @author Gargantua丶
 **/
data class Product(
    var name: String? = null,
    var id: Int? = null,
    var length: Int? = null,
    var height: Int? = null,
    var weight: Int? = null
) {
    init {
        require(name != null || id != null) {
            "id and name are not allowed to be null at the same time"
        }
    }
}

interface Builder {
    fun build(): Product
}