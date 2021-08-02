# 命令模式

> 命令模式是对命令的封装，每一个命令就一个操作。命令模式解耦了请求方和操作方，请求方只需要请求命令，不需要关心执行过程

***

## 命令模式的应用场景

+ 语义中具有 [命令] 的操作
+ 请求的调用者和接收者需要解耦，使得调用者不用直接交互
+ 需要抽象出等待执行的行为
+ 需要命令组合操作（命令宏）



## 命令模式的组成角色

+ 接收者角色

  负责具体实施或者实行的请求

  ```kotlin
  class Receiver {
      
      fun request() {
          println("Requesting in Receiver")
      }
  }
  ```

+ 抽象命令角色

  定义需要执行命令的行为接口

  ```kotlin
  interface AbstractCommand {
      
      fun execute()
  }
  ```

+ 具体命令角色

  内部维护一个`Receiver`接收者对象，在对应方法中调用接收者方法

  ```kotlin
  class ConcreteCommand : AbstractCommand {
  
      private val receiver = Receiver()
  
      override fun execute() {
          receiver.request()
      }
  }
  ```

+ 请求者角色

  接收客户端的命令，并且执行命令

  ```kotlin
  class Invoker(private val command: AbstractCommand) {
  
      fun request() = command.execute()
  }
  ```

  

## 命令模式的测试调用

```kotlin
Invoker(ConcreteCommand()).request()
```

请求者接收客户端的命令并且执行命令即可，结果如下

> Requesting in Receiver



## 命令模式的优缺点

### 命令模式的优点

+ 通过引入接口，解耦了命令请求与实现
+ 扩展性好，可以直接增加新命令
+ 支持组合队列和命令队列
+ 增加额外功能更加灵活



### 命令模式的缺点

+ 增加系统复杂度和抽象程度
+ 命令类可能过多

