# 抽象工厂模式

> 抽象工厂模式提供创建一系列相关或相互依赖对象的接口，而无须指定具体类型

***

## [产品族和产品等级结构](https://www.jianshu.com/p/f1e837cab952)



## 抽象工厂模式的组成

+ 抽象工厂

  ```kotlin
  interface AbstractFactory {
      fun createProductA(): AbstractProductA
      fun createProductB(): AbstractProductB
  }
  ```

+ 具体工厂

  一个工厂类提供了同一个产品等级的不同产品族的对象创建

  ```kotlin
  object FamilyAFactory : AbstractFactory() {
      override fun createProductA(): AbstractProductA = ProductAWithFamilyA()
  
      override fun createProductB(): AbstractProductB = ProductBWithFamilyA()
  }
  
  object FamilyBFactory : AbstractFactory() {
      override fun createProductA(): AbstractProductA = ProductAWithFamilyB()
  
      override fun createProductB(): AbstractProductB = ProductBWithFamilyB()
  }
  ```

+ 抽象产品

  工厂创建的所有产品的父类，负责描述所有实例共有的公共接口

  ```kotlin
  interface AbstractProductA : AbstractProduct
  interface AbstractProductB : AbstractProduct
  ```

+ 具体产品

  工厂的创建目标

  ```kotl
  class ProductAWithFamilyA : AbstractProductA
  class ProductBWithFamilyA : AbstractProductB
  
  class ProductAWithFamilyB : AbstractProductA
  class ProductBWithFamilyB : AbstractProductB
  ```

  

## 调用抽象工厂模式创建对象

### 一般调用方式

```kotlin
FamilyAFactory.createProductA().onCreate()
FamilyAFactory.createProductB().onCreate()
FamilyBFactory.createProductA().onCreate()
FamilyBFactory.createProductB().onCreate()
```

同样还是直接调用



### Kotlin内联方法简化调用

> [内联函数](https://www.kotlincn.net/docs/reference/inline-functions.html) - Kotlin语言中文站

将`AbstractFactory`修改为以下抽象类

```kotlin
abstract class AbstractFactory {
    abstract fun createProductA(): AbstractProductA
    abstract fun createProductB(): AbstractProductB

    companion object {
        inline operator fun <reified T : AbstractProduct> invoke(): AbstractFactory {
            return when(T::class) {
                ProductAWithFamilyA::class, ProductBWithFamilyA::class -> FamilyAFactory
                ProductAWithFamilyB::class, ProductBWithFamilyB::class -> FamilyBFactory
                else -> throw IllegalArgumentException()
            }
        }
    }
}
```

之后使用以下方式调用

```kotlin
AbstractFactory<ProductAWithFamilyA>().createProductA().onCreate()
AbstractFactory<ProductBWithFamilyA>().createProductB().onCreate()
AbstractFactory<ProductAWithFamilyB>().createProductA().onCreate()
AbstractFactory<ProductBWithFamilyB>().createProductB().onCreate()
```

此方式的优点在于直接向抽象方法内传递所需要的具体产品类型即可获得对应的工厂

缺点是添加新工厂时需要修改抽象工厂方法



## 抽象工厂模式的优缺点

### 优点

+ 需要产品族时，抽象工厂可以保证只使用一个产品的产品族
+ 添加新产品族时只需要创建一个新的具体工厂即可

### 缺点

+ 同一个产品族中增加新产品会比较困难，需要修改具体工厂