# 简单工厂模式

> 简单工厂**不是**一种设计模式

***

## 简单工厂的组成

+ 抽象产品

  简单工厂创建的所有产品的父类，负责描述所有实例共有的公共接口

  ```kotlin
  interface AbstractProduct {
      fun onCreate() {
          println("$this onCreate")
      }
  }
  ```

+ 具体产品

  简单工厂模式的创建目标

  ```kotl
  class ProductA: AbstractProduct
  class ProductB: AbstractProduct
  ```

+ 简单工厂

  简单工厂模式的核心，负责实现创建所有实例的内部逻辑。工厂类的创建产品类的方法可以被外部直接调用，创建所需的产品对象

  简单工厂模式仅有一个具体的工厂类，所有产品的创建都在此工厂类中

> 为便于向简单工厂内传递参数，我们创建一个枚举类枚举现有所有产品
>
> ```kotlin
> enum class ProductType {
>     ProductA,
>     ProductB
> }
> ```



## 简单工厂模式的实现

### 简单工厂的一般实现

```kotlin
class SimpleFactory {
    operator fun invoke(type: ProductType) =
        when (type) {
            ProductType.ProductA -> ProductA()
            ProductType.ProductB -> ProductB()
        }
}
```

> [Kotlin 调用操作符 `invoke`](https://www.kotlincn.net/docs/reference/operator-overloading.html#invoke) - Kotlin语言中文站

通过简单工厂对象并传入枚举类型直接获得具体产品

```kotlin
val simpleFactory = SimpleFactory()
//ProductA@115c6b1e onCreate
simpleFactory(ProductType.ProductA).onCreate()
//ProductB@18273edb onCreate
simpleFactory(ProductType.ProductB).onCreate()
```



### 简单工厂的静态类实现

> 由于工厂类可以认为是单例的，所以在Kotlin中可以将工厂类改为静态的Object类

```kotlin
object SimpleStaticFactory {
    operator fun invoke(type: ProductType) =
        when (type) {
            ProductType.ProductA -> ProductA()
            ProductType.ProductB -> ProductB()
        }
}
```

调用方式与一般实现大抵相同，只是不再需要创建工厂对象

```kotlin
SimpleStaticFactory(ProductType.ProductA).onCreate()
SimpleStaticFactory(ProductType.ProductB).onCreate()
```



## 简单工厂模式的优缺点

### 优点

简单工厂模式结构简单，调用方便。可以很方便的创建出产品



### 缺点

简单工厂模式的工厂类单一，一旦有新产品时需要修改简单工厂类，违背开闭原则。工厂类代码也会变得逐渐臃肿，违背高聚合原则。**仅适合产品类型较少的情况**

**故不认为简单工厂模式是一种设计模式**

