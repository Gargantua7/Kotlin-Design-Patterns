# 策略模式

> 策略模式将算法家族分别封装起来，让他们可以互相替换，从而让算法的变化不会影响使用算法的用户

***

## 策略模式的应用场景

+ 同一类型问题，有多种处理方式，每一种都能独立解决问题
+ 需要自由切换算法，屏蔽算法规则的场景



## 策略模式的组成角色

+ 上下文角色

  用于操作策略的上下文，屏蔽高层对策略算法的直接访问，封装可能存在的变化

+ 抽象策略角色

  规定策略或算法的行为

+ 具体策略角色

  具体的策略或算法实现



## 策略模式的实现方式

### 策略模式的一般实现方式

+ 上下文角色

  ```kotlin
  class Context(private val strategy: AbstractStrategy) {
  
      fun request() = strategy.request()
  }
  ```

+ 抽象策略角色

  ```kotlin
  interface AbstractStrategy {
      fun request()
  }
  ```

+ 具体策略角色

  ```kotlin
  class ConcreteStrategyA : AbstractStrategy {
  
      override fun request() {
          println("Requesting in Concrete A")
      }
  }
  ```

  ```kotlin
  class ConcreteStrategyB : AbstractStrategy {
  
      override fun request() {
          println("Requesting in Concrete B")
      }
  }
  ```



#### 策略模式的一般实现的测试调用

```kotlin
Context(ConcreteStrategyA()).request()
Context(ConcreteStrategyB()).request()
```

结果如下：

> Requesting in Concrete A
>
> ***
>
> Requesting in Concrete B



### 策略模式使用 Kotlin 高阶函数的实现方式

+ 上下文角色

  在构造器定义一个默认的执行算法

  ```kotlin
  class Context(private val method: () -> Unit = { println("Requesting in Default Method") }) {
  
      fun request() = method()
  }
  ```

+ 具体策略模式

  跳过抽象策略模式，由 lambda 参数组成

  ```kotlin
  val strategyA = { println("Requesting in Strategy A") }
  
  val strategyB = { println("Requesting in Strategy B") }
  ```

  

#### 策略模式使用 Kotlin 高阶函数的实现方式的测试调用

```kotlin
Context().request()
Context(strategyA).request()
Context(strategyB).request()
// 同样也可以使用自定义 lambda
Context { println("Requesting in Customize Lambda") }.request()
```

结果如下：

> Requesting in Default Method
>
> ***
>
> Requesting in Strategy A
>
> ***
>
> Requesting in Strategy B
>
> ***
>
> Requesting in Customize Lambda



## 策略模式的优缺点

### 策略模式的优点

+ 符合开闭原则
+ 避免使用多重条件转移语句
+ 提高算法安全性



### 策略模式的缺点

+ 客户必须要知道所有策略，并且决定使用哪一个
+ 增加维护难度