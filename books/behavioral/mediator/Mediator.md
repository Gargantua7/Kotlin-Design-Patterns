# 中介者模式

> 用一个中介对象封装一系列对象交互，中介者使各对象不需要显示的相互作用，使其耦合松散，而且可以独立的改变它们之间的交互

***

## 应用场景

+ 系统中对象存在复杂的引用关系，产生相互依赖的关系结构混乱且难以理解
+ 交互的公共行为，如需改变行为，可以增加中介者类



## 组成角色

+ 抽象中介者

  定义中介者的统一接口，用于各个同事角色间通信

  ```kotlin
  abstract class AbstractMediator {
  
      protected val colleagueA by lazy {  ColleagueA(this) }
      protected val colleagueB by lazy {  ColleagueB(this) }
  
      abstract fun transferA()
      abstract fun transferB()
  }
  ```

+ 具体中介者

  从具体的同事对象接收消息，向具体同事发出指令，协调同事角色间协作

  ```kotlin
  class Mediator : AbstractMediator() {
  
      override fun transferA() {
          colleagueB.self()
      }
  
      override fun transferB() {
          colleagueA.self()
      }
  }
  ```

+ 抽象同事类

  每一个同事均依赖中介者角色，交由中介者进行转发协作

  ```kotlin
  abstract class AbstractColleague(val mediator: AbstractMediator) {
      abstract fun self()
      abstract fun dependent()
  }
  ```

+ 具体同事类

  负责实现自发行为，转发依赖行为交由中介者协调

  ```kotlin
  class ColleagueA(mediator: AbstractMediator) : AbstractColleague(mediator) {
  
      override fun self() {
          println("$this.self")
      }
  
      override fun dependent() {
          println("$this.dependent")
          mediator.transferA()
      }
  }
  ```

  ```kotlin
  class ColleagueB(mediator: AbstractMediator) : AbstractColleague(mediator) {
  
      override fun self() {
          println("$this.self")
      }
  
      override fun dependent() {
          println("$this.dependent")
          mediator.transferB()
      }
  }
  ```



## 测试调用

```kotlin
val mediator = Mediator()
ColleagueA(mediator).dependent()
ColleagueB(mediator).dependent()
```

结果如下

>ColleagueA@1681af18.dependent
>
>ColleagueB@6733080d.self
>***
>ColleagueB@7e35c7ac.dependent
>ColleagueA@57f5d058.self



## 优缺点

+ **优点**
  + 减少类间依赖，将多对多依赖转为一对多，降低耦合
  + 符合迪米特法则
+ **缺点**
  + 同事类过多时，中介者将会非常臃肿，难以维护



***
> 以下链接请在 GitHub 中使用
>
> GitHub Pages请点击页面最上方的 "View on GitHub"
>
> + [具体业务源代码](../../../src/main/kotlin/club/gargantua7/design_patterns/behavioral/mediator)
> + [测试用例源代码](../../../src/test/kotlin/club/gargantua7/design_patterns/behavioral/mediator)
