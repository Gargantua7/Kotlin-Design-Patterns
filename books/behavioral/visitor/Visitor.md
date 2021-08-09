# 访问者模式

> 访问者模式是将数据结构与数据分离的设计模式，指封装一些作用于某种数据结构中的各元素的操作，可以在不改变数据结构的前提下定义作用于这些元素的新操作

***

## 应用场景

+ 数据结构稳定，作用于数据结构的操作经常变化的场景
+ 需要数据结构与数据操作分离的场景
+ 需要对不同数据类型（元素）进行操作，而不使用分支判断具体类型的场景



## 组成角色

+ 抽象访问者

  定义`visit`方法用于访问每一个具体元素，其参数就是具体的元素对象。一般来说，方法个数与元素个数是相等的。如果元素个数经常发生变化，则导致方法也需要变动则不适用访问者模式

  ```kotlin
  interface AbstractVisitor {
  
      fun visit(e: ElementA)
      fun visit(e: ElementB)
  }
  ```

+ 具体访问者

  实现对具体元素的操作

  ```kotlin
  class VisitorA : AbstractVisitor {
  
      override fun visit(e: ElementA) {
          println("result form ${javaClass.simpleName} : ${e.request()}")
      }
  
      override fun visit(e: ElementB) {
          println("result form ${javaClass.simpleName} : ${e.request()}")
      }
  }
  ```

  ```kotlin
  class VisitorB : AbstractVisitor {
  
      override fun visit(e: ElementA) {
          println("result form ${javaClass.simpleName} : ${e.request()}")
      }
  
      override fun visit(e: ElementB) {
          println("result form ${javaClass.simpleName} : ${e.request()}")
      }
  }
  ```

+ 抽象元素

  定义一个接受访问的方法，表示所有元素都支持被访问者对象访问

  ```kotlin
  interface AbstractElement {
  
      fun accept(visitor: AbstractVisitor)
  }
  ```

  

+ 具体元素

  具体元素类型，提供接受访问者的具体实现

  ```kotlin
  class ElementA : AbstractElement {
  
      override fun accept(visitor: AbstractVisitor) {
          visitor.visit(this)
      }
  
      fun request(): String = javaClass.simpleName
  }
  ```

  ```kotlin
  class ElementB : AbstractElement {
  
      override fun accept(visitor: AbstractVisitor) {
          visitor.visit(this)
      }
  
      fun request(): String = javaClass.simpleName
  }
  ```

+ 结构对象

  内部维护了元素集合，并提供方法接受对该集合所有元素进行操作

  ```kotlin
  class Structure {
  
      private val list = ArrayList<AbstractElement>()
  
      init {
          list.add(ElementA())
          list.add(ElementB())
      }
  
      fun accept(visitor: AbstractVisitor) {
          list.forEach { e -> e.accept(visitor) }
      }
  }
  ```



## 测试调用

```kotlin
val structure = Structure()
structure.accept(VisitorA())
structure.accept(VisitorB())
```

结果如下：

> result form VisitorA : ElementA
>
> result form VisitorA : ElementB
>
> ***
>
> result form VisitorB : ElementA
>
> result form VisitorB : ElementB



## 优缺点

+ **优点**
  + 解耦了数据结构与数据操作，是的操作集合可以独立变化
  + 可以通过扩展访问者的角色，实现对数据的不同操作，程序扩展性更好
  + 元素具体类型并非单一，访问者均可操作
  + 各角色职责分离，符合单一职责原则
+ **缺点**
  + 不能增加元素类型
  + 具体元素变更困难
  + 违背依赖倒置原则


***
> 以下链接请在 GitHub 中使用
>
> GitHub Pages请点击页面最上方的 "View on GitHub"
>
> + [具体业务源代码](../../../src/main/kotlin/club/gargantua7/design_patterns/behavioral/visitor)
> + [测试用例源代码](../../../src/test/kotlin/club/gargantua7/design_patterns/behavioral/visitor)
