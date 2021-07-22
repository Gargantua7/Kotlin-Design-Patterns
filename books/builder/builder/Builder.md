# 建造者模式

> 建造者模式将一个复杂对象的构建过程与它的表示分离

> 适用于生产比较复杂的产品，而产品可以使用的零件属性非常多种，不同装配方式拥有不同结果

***

## 建造者模式的组成

+ 产品

  创建的产品对象

  ```kotlin
  data class Product(
      var name: String? = null,
      var id: Int? = null,
      var length: Int? = null,
      var height: Int? = null,
      var weight: Int? = null
  )
  ```

+ 抽象建造者

  建造者的抽象接口，规范产品对象的创建

  ```kotlin
  interface Builder {
      fun build(): Product
  }
  ```

+ 具体建造者

  具体的建造者，拥有不同的逻辑，装配出不同的对象

  

## 具体建造者的实现方式

### 一般实现方式

```kotlin
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
```

`build`方法中添加了约束，表示不允许产品又无`name`又无`id`

调用方式如下

```kotlin
val product = GeneralBuilder().run {
    setName("P1")
    setId(1)
    setLength(50)
    setHeight(50)
    setWeight(50)
    build()
}
println(product)
```

结果如下

> Product(name=P1, id=1, length=50, height=50, weight=50)



### 链式调用实现方式

将具体建造者所有`set`方法返回值改为建造者本身，如：

```kotlin
fun setName(name: String): ChainBuilder {
    product.name = name
    return this
}
```

可进行链式调用

```kotlin
val product = ChainBuilder()
    .setName("P2")
    .setId(2)
    .setLength(50)
    .setHeight(50)
    .setWeight(50)
    .build()
println(product)
```

结果如下

> Product(name=P2, id=2, length=50, height=50, weight=50)

链式调用相比一般调用更具有语义性，也更好理解



## Kotlin最佳实现方式

> **Kotlin 中应尽量避免使用建造者模式**

Kotlin 的方法可以使用更简单且更具语义化的具名可选参数

> [默认参数](https://www.kotlincn.net/docs/reference/functions.html#%E9%BB%98%E8%AE%A4%E5%8F%82%E6%95%B0) - Kotlin语言中文站

> [具名参数](https://www.kotlincn.net/docs/reference/functions.html#%E5%85%B7%E5%90%8D%E5%8F%82%E6%95%B0) - Kotlin语言中文站

在Kotlin中我们可以使用以下方式直接创建一个新的对象

```kotlin
Product(id = 3, name = "P3")
```

> 前提是所需参数拥有默认参数

#### 在可选具名参数中实现约束以替代建造者模式

建造者模式一个很重要的特点就是可以在`build`方法中自定义逻辑，保证构建对象安全

Kotlin也可以在构造器中对参数直接进行约束，若不符合约束则抛出异常

```kotlin
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
```

还是不允许产品又无`name`又无`id`

以下是`require`方法的逻辑部分

```kotlin
public inline fun require(value: Boolean, lazyMessage: () -> Any): Unit {
    if (!value) {
        val message = lazyMessage()
        throw IllegalArgumentException(message.toString())
    }
}
```

其原理也非常简单易懂，判断是否符合条件，否则抛出异常

我们此时直接调用`Product()`会出现以下报错

> java.lang.IllegalArgumentException: id and name are not allowed to be null at the same time



即除非在极为复杂的情况下，Kotlin 默认具名可选参数是足以直接替代建造者方法的



## 建造者模式与工厂模式的区别

+ 建造者模式更注重方法调用顺序，而工厂模式只在于创建对象

+ 建造者模式可以由用户自定义创造的对象，而工厂模式创建都对象都一样

+ 建造者模式需要知道对象的组成，而工厂模式只需要把对象创建出来就可以

  

## 建造者模式的优缺点

### 优点

+ 建造者模式封装性较高
+ 建造者模式拥有一定程度的解耦

### 缺点

+ 建造者模式后期维护成本较大