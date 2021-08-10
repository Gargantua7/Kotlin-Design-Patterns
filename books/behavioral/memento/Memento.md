# 备忘录模式

> 在不破坏封装的前提下，捕获一个对象的内部状态，并在对象之外保存这个状态，之后可以将该对象恢复为原先保存的形态

***

## 应用场景

+ 需要保存历史快照的场景（类似于Git）
+ 希望在对象外保存，且不公开访问权限（黑箱备忘录模式）



## 组成角色

+ 备忘录角色

  用于存储发起人角色的内部状态

  + 白箱模式

    直接创建可以公开访问的备忘录类，此模式不安全

    ```kotlin
    data class Memento(val state: String)
    ```

  + 黑箱模式

    仅对外公开接口，接口内不包含任何关键方法

    ```kotlin
    interface AbstractMemento
    ```

+ 发起人角色

  被保存的对象的类，复制创建备忘录保存内部状态，并具备回滚功能

  + 白箱模式

    ```kotlin
    class Originator(var state: String) {
    
        fun createMemento(): Memento = Memento(state)
        fun restoreMemento(memento: Memento) {
            state = memento.state
        }
    }
    ```

  + 黑箱模式

    将实际备忘录角色作为私有内部类，可避免实际备忘录被外部访问

    ```kotlin
    class Originator(var state: String) {
    
        fun createMemento(): AbstractMemento = Memento(state)
        fun restoreMemento(memento: AbstractMemento) {
            state = (memento as Memento).state
        }
    
        private data class Memento(val state: String): AbstractMemento
    }
    ```
  
+ 备忘录管理员角色

  黑箱白箱写法一致，只负责存储，故不需要访问备忘录的方法
  
  本例使用`HashMap`存储多个版本并记录版本号
  
  ```kotlin
  class Caretaker {
  
      private val map = HashMap<Int, Memento>()
  
      operator fun get(version: Int) = map[version]
  
      operator fun set(version: Int, memento: Memento) {
          map[version] = memento
      }
  }
  ```
  
  

## 测试调用

黑白箱调用写法一致

```kotlin
val originator = Originator("A")
val caretaker = Caretaker()
caretaker[1] = originator.createMemento()
originator.state = "B"
caretaker[2] = originator.createMemento()
originator.state = "C"
originator.restoreMemento(caretaker[1]!!)
println(originator.state)
originator.restoreMemento(caretaker[2]!!)
println(originator.state)
```

结果如下

>A
>
>B

> 实际上白箱在外部可以访问备忘录角色内部状态，而黑箱不行



## 优缺点

+ **优点**
  + 隔离状态存储与获取
  + 提供回滚功能
+ **缺点**
  + 消耗资源较多
  
***
> 以下链接请在 GitHub 中使用
>
> GitHub Pages请点击页面最上方的 "View on GitHub"
>
> + [具体业务源代码](../../../src/main/kotlin/club/gargantua7/design_patterns/behavioral/memento)
> + [测试用例源代码](../../../src/test/kotlin/club/gargantua7/design_patterns/behavioral/memento)
