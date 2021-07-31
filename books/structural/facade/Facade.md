# 门面模式

> 门面模式定义了一个高层接口，让子系统更容易使用

***

## 门面模式的适用场景

+ 为一个复杂的模块提供一个简洁的外部访问接口
+ 提高子系统独立性
+ 当子系统不可避免的存在问题时，通过门面模式隔离客户端与子系统交互



## 门面模式的组成角色

+ 子系统角色

  由多个有关无关的子系统模块组成

  ```kotlin
  class ClassA {
      fun request() {
          println("Class A is Requested")
      }
  }
  
  class ClassB { /* ...*/ }
  class ClassC { /* ...*/ }
  ```

+ 外观角色

  门面模式中对外的接口，对于子系统而言，这就是一个客户端

  ```kotlin
  object Facade {
  
      private val classA = ClassA()
      private val classB = ClassB()
      private val classC = ClassC()
  
      fun requestA() {
          classA.request()
      }
      fun requestB() {
          classB.request()
      }
      fun requestC() {
          classC.request()
      }
  }
  ```



## 门面模式的测试调用

门面模式的代码和测试都非常简单

```kotlin
Facade.requestA()
Facade.requestB()
Facade.requestC()
```

结果如下

>Class A is Requested
>
>Class B is Requested
>
>Class C is Requested



## 门面模式的优缺点

### 门面模式的优点

+ 简化调用过程，不用深入了解子系统
+ 减少系统依赖，降低耦合
+ 方便划分访问层次，提高安全性
+ 遵循迪米特法则



### 门面模式的缺点

+ 增加子系统时会带来一定风险
+ 不符合开闭原则，某些情况违背单一职责原则