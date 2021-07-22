# 工厂方法模式

>工厂方法模式定义了抽象的工厂接口，由实现此接口的具体工厂类决定实例化哪个类

***

## 工厂方法模式的组成

+ 抽象工厂

  工厂方法模式的核心。不直接创建任何对象，但所有创建对象的工厂必须实现此接口

  ```kotlin
  private interface AbstractFactoryMethod {
      operator fun invoke(): AbstractProduct
  }
  ```

+ 具体工厂

  实现抽象工厂接口的具体工厂，是创建对象的具体类

  ```kotlin
  object ProductAFactory: AbstractFactoryMethod {
      override operator fun invoke() = ProductA()
  }
  
  object ProductBFactory: AbstractFactoryMethod {
      override operator fun invoke() = ProductB()
  }
  ```

+ 抽象产品

  工厂创建的所有产品的父类，负责描述所有实例共有的公共接口

  ```kotlin
  interface AbstractProduct {
      fun onCreate() {
          println("$this onCreate")
      }
  }
  ```

+ 具体产品

  工厂模式的创建目标

  ```kotl
  class ProductA: AbstractProduct
  class ProductB: AbstractProduct
  ```

  

## 调用工厂方法模式创建对象

```kotlin
ProductAFactory().onCreate()
ProductBFactory().onCreate()
```

直接调用即可创建对象



## 工厂方法模式的优缺点

### 优点

+ 灵活性较简单工厂模式更强，添加新产品无需更改原有代码，满足开闭原则
+ 耦合度教简单工厂模式降低，高层模块仅需了解抽象类，而无需关心其他实现。满足迪米特法则，依赖倒置原则，里氏替换原则

### 缺点

+ 随着产品变多将增加复杂度
+ 一个工厂基本只能生产一个具体产品