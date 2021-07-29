# 组合模式

> 组合模式通过将单个对象（叶子节点）和组合对象（树枝节点）用相同的接口表示，是的单个对象和组合对象具有一致性

> 实现方式与树形结构相似

***

## 组合模式的应用场景

+ 希望客户端可以忽略组合对象和单个对象的差异
+ 对象层次具备整体和部分，呈树形结构



## 组合模式的实现

> 组合模式通过区分透明性和安全性分为**透明组合模式**和**安全组合模式**

### 透明组合模式

#### 透明组合模式组成

+ 通用接口

  ```kotlin
  abstract class Component(protected val name: String, val depth: Int) {
  
      abstract fun operation()
  
      open operator fun plusAssign(component: Component) {
          throw UnsupportedOperationException()
      }
  
      open operator fun minusAssign(component: Component) {
          throw UnsupportedOperationException()
      }
  
      open operator fun get(index: Int): Component {
          throw UnsupportedOperationException()
      }
  }
  ```

  > [操作符重载](https://www.kotlincn.net/docs/reference/operator-overloading.html) - Kotlin 语言中文站

  通用接口中包含了对象的`name`字段和`depth`也就是深度字段，还有一个抽象方法以及三个实现方法，但是这三个方法都会抛出`UnsupportedOperationException`，目的是不让外部直接调用，但这样是不安全的

+ 组合对象

  ```kotlin
  class Composite(name: String, depth: Int) : Component(name, depth) {
  
      private val components = ArrayList<Component>()
  
      override fun operation() {
          println("${ buildString { for (i in 1..depth) append("  ")}}$name")
          components.forEach { component -> component.operation() }
      }
  
      override fun plusAssign(component: Component) {
          components += component
      }
  
      override fun minusAssign(component: Component) {
          components -= component
      }
  
      override fun get(index: Int): Component {
          return components[index]
      }
  }
  ```

  组合对象实现并重写了通用的接口中的所有方法，并自己实现了三个`UnsupportedOperationException`的方法。由于本对象由多个对象组合而来，所以维护了一个`List`列表

+ 单个对象

  ```kotlin
  class Left(name: String, depth: Int) : Component(name, depth) {
  
      override fun operation() {
          println("${ buildString { for (i in 1..depth) append("  ")}}$name")
      }
  }
  ```

  单个对象不需要维护列表，所以仅需要实现抽象成员即可

#### 透明组合模式的调用测试

```kotlin
val root = Composite("root", 0)
val composite1d1 = Composite("Composite 1-1", 1)
val composite1d2 = Composite("Composite 1-2", 1)
root += composite1d1
root += composite1d2
composite1d1 += Left("Left 2-1", 2)
root.operation()
```

结果如下

> root
> 
>  ​ ​ ​ Composite 1-1
>
>  ​ ​ ​ ​ ​ ​ ​ Left 2-1
>
>  ​ ​ ​ Composite 1-2



### 安全组合模式

透明模式中的通用接口的三个方法虽然抛出了`UnsupportedOperationException`异常，但并不能阻止客户在单个对象中使用该方法，所以这是不安全的

即安全的模式是不在通用接口内定义这三个方法

而单独的在组合对象中定义，则单个对象则没有这三个方法

缺点是结构不够透明

如果系统不需要单个对象的话，采用透明组合模式更好

> 实现较为简单，故不再赘述



## 组合模式的优缺点

### 组合模式的优点

+ 清楚地定义各层次的复杂对象，表示对象的全部或部分层次
+ 简化代码，符合开闭原则



### 组合模式的缺点

+ 限制类型时会较为复杂
+ 使设计变得抽象
