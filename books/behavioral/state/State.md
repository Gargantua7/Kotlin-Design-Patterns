# 状态模式

> 状态模式中行为由状态决定，在不同的状态下有不同的行为

***

## 状态模式的应用场景

+ 行为随状态改变
+ 一个操作中含有庞大的多分支结构，并且这些分支取决于对象状态



## 状态模式的组成角色

+ 抽象状态角色

  维护上下文角色并且定义客户端需要的接口

  ```kotlin
  abstract class AbstractState(protected val context: Context) {
  
      abstract fun handler()
  }
  ```

+ 具体状态角色

  实现抽象状态角色，并且决定处理请求方式和行为

  此例具体实现为处理完请求后切换上下文角色中的状态为另一个状态角色

  ```kotlin
  class StateA(context: Context) : AbstractState(context) {
  
      override fun handler() {
          println("A is Handling the Request")
          context.currState = context.stateB
      }
  }
  ```

  ```kotlin
  class StateB(context: Context) : AbstractState(context) {
  
      override fun handler() {
          println("B is Handling the Request")
          context.currState = context.stateA
      }
  }
  ```

+ 上下文角色

  维护当前状态并且将请求转发给具体状态角色

  ```kotlin
  class Context {
  
      val stateA = StateA(this)
      val stateB = StateB(this)
  
      var currState: AbstractState = stateA
  
      fun handler() = currState.handler()
  }
  ```

  

## 状态模式的测试调用

```kotlin
val context = Context()
context.handler()
context.handler()
context.handler()
```

结果如下

> A is Handling the Request
>
> B is Handling the Request
>
> A is Handling the Request

可见每一次处理请求后的下一次处理为另一个状态角色



## 状态模式和策略模式的区别

+ 状态模式的状态由系统内部维护和决定
+ 策略模式的处理行为由客户端决定



## 状态模式的优缺点

+ **优点**
  + 结构清晰，避免不必要的`if`/`when`嵌套
  + 将状态切换现实化
  + 状态类职责明确且具备扩展性
+ **缺点**
  + 类膨胀
  + 结构复杂
  + 并不完全符合开闭原则

