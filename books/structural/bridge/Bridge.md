# 桥接模式

> 桥接模式指将抽象部分与集体实现部分分离，使它们都可以独立地变化

***

## 桥接模式的适用场景

+ 在抽象和具体实现之间需要增加更多灵活性的场景
+ 一个类存在多个变化维度，而这些维度都需要独立扩展



## 桥接模式的组成角色

+ 抽象

  该类持有一个对实现角色的引用，抽象角色中的方法需要实现角色实现

  ```kotlin
  abstract class Abstraction(protected open val abstractImplementor: AbstractImplementor) {
      open fun request() {
          abstractImplementor.request()
      }
  }
  ```

+ 修正抽象

  抽象的具体实现，对抽象的方法进行完善和扩展

  ```kotlin
  class RefinedAbstraction(abstractImplementor: AbstractImplementor) : Abstraction(abstractImplementor) {
      override fun request() {
          println("Refined Abstraction is Requested")
          super.request()
      }
  }
  ```

+ 实现

  确定实现维度的基本操作，提供给抽象使用

  ```kotlin
  interface AbstractImplementor {
      fun request()
  }
  ```

+ 具体实现

  ```kotlin
  class ImplementorA : AbstractImplementor {
      override fun request() {
          println("Implementor A is Requested")
      }
  }
  ```



## 桥接模式的测试调用

```kotlin
RefinedAbstraction(ImplementorA()).request()
```

结果如下：

> Refined Abstraction is Requested
>
> Implementor A is Requested



## 桥接模式的优缺点

### 桥接模式的优点

+ 分离抽象部分及其具体实现部分
+ 提高系统扩展性
+ 符合开闭原则、合成复用原则



### 桥接模式的缺点

+ 增加系统复杂度和设计难度