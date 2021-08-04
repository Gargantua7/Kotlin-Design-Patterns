# 责任链模式

> 责任链模式将链中每一个结点都看作一个对象，每个结点的处理的请求都不一样，且内部维护下一个对象。当一个请求从链首发出时，会沿着责任链预设的路径依次传递到每一个节点对象，直到被某个对象处理为止

***

## 责任链模式的应用场景

+ 多个对象可以处理同一请求，但具体由谁处理在运行时动态决定
+ 在不明确接收者的情况下，向多个对象提交请求
+ 可动态指定一组对象的处理请求



## 责任链模式的组成角色

+ 抽象处理者

  定义请求处理的方法，并维护下一个处理结点

  此处抽象了`check`方法和`handler`方法由子类实现，决定是否处理以及处理方式，并统一负责请求传递。若无下一处理者代表请求不被支持，抛出异常

  ```kotlin
  abstract class AbstractHandler(private val nextHandler: AbstractHandler?) {
  
      fun request(target: String) {
          if (check(target)) {
              handler()
          } else {
              println("Unsupported Request in $this")
              nextHandler?.request(target) ?: throw IllegalArgumentException("$target isn't supported")
          }
      }
  
      abstract fun check(target: String): Boolean
  
      abstract fun handler()
  }
  ```

+ 具体处理者

  具体决定是否处理和处理方式的对象

  ```kotlin
  class HandlerA(nextHandler: AbstractHandler? = null) : AbstractHandler(nextHandler) {
  
      override fun check(target: String): Boolean = target == "A"
  
      override fun handler() {
          println("Requesting in $this")
      }
  }
  ```

  ```kotlin
  class HandlerB(nextHandler: AbstractHandler? = null) : AbstractHandler(nextHandler) {
  
      override fun check(target: String): Boolean = target == "B"
  
      override fun handler() {
          println("Requesting in $this")
      }
  }
  ```



## 责任链模式的测试调用

```kotlin
val handler = HandlerA(HandlerB())
handler.request("A")
handler.request("B")
```

结果如下：

> Requesting in HandlerA@78a9a98e
>
> ***
>
> Unsupported Request in HandlerA@78a9a98e
>
> 
>
> Requesting in HandlerB@1f6ded79



## 责任链模式使用 Kotlin 偏函数的特殊实现

> [函数类型实例化](https://www.kotlincn.net/docs/reference/lambdas.html#%E5%87%BD%E6%95%B0%E7%B1%BB%E5%9E%8B%E5%AE%9E%E4%BE%8B%E5%8C%96) - Kotlin 语言中文站

首先定义偏函数`Chain`类，类型为`(String) -> Unit`的函数类型化

```kotlin
class Chain(val check: (String) -> Boolean, private val handler: (String) -> Unit) : (String) -> Unit {

    override fun invoke(target: String) {
        return if (check(target)) handler(target) else throw IllegalArgumentException("$target isn't supported")
    }
}
```

类构造器接收两个 lambda 参数，分别是负责判断是否处理的`check`方法和如何处理的`handler`方法

`invoke`方法的函数类型实例化必须实现的方法，内部逻辑是调用`check`方法判断，如果应该处理则调用`handler`方法直接处理，否则抛出异常



具体处理者则直接实现`Chain`类

```kotlin
val HandlerA = Chain({ it == "A" }) { println("Requesting in A") }
```

```kotlin
val HandlerB = Chain({ it == "B" }) { println("Requesting in B") }
```



为使具体处理者串为链，同时兼顾语义化，实现以下方法

```kotlin
infix fun Chain.to(that: Chain) = Chain({ check(it) || that.check(it) }) {
    if (check(it)) this(it) else that(it)
}
```

> [中缀表示法](https://www.kotlincn.net/docs/reference/functions.html#%E4%B8%AD%E7%BC%80%E8%A1%A8%E7%A4%BA%E6%B3%95) - Kotlin 语言中文站

该方法将左右两边的处理者串起来并返回`Chain`对象，逻辑类似于递归，只要接下来的链中有一个能处理则`check`返回`true`，再路由至能处理的处理者



具体测试调用如下

```kotlin
val chain = HandlerA to HandlerB
chain("A")
chain("B")
```

结果如下

>Requesting in A
>
>***
>
>Requesting in B



## 责任链模式的优缺点

### 责任链模式的优点

+ 请求与处理解耦
+ 处理者只需关注自己可以处理的请求即可，无法处理的请求直接转发给下一个节点对象
+ 具备链式传递处理请求功能，请求发送者不需要知晓链路结构，只需等待请求处理结果即可
+ 链路结构灵活，可以通过改变链路结构动态地新增或删减责任
+ 易于扩展新的请求处理类，符合开闭原则



### 责任链模式的缺点

+ 责任链太长会影响性能
+ 如果存在循环引用会出现死循环并导致系统崩溃

