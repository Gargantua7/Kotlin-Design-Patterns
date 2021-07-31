# 模板方法模式

> 指定义一个操作中的算法的框架，而将一些步骤延迟到子类中，使得子类不修改算法结构即可重定义某些特定步骤

***

## 模板方法模式的应用场景

+ 一次性实现一个算法不变的部分，将可变的行为留给子类实现
+ 各子类中公共的行为被提取出来，集中到一个公共的父类中，从而避免代码重复



## 模板方法模式的组成角色

+ 抽象模板

  定义了一套算法框架和流程

+ 具体实现

  对算法框架和流程的某些步骤进行了实现



## 模板方法模式的实现方式

### 模板方法模式的一般实现方式

+ 抽象模板

  ```kotlin
  abstract class AbstractTemplate {
  
      protected open fun before() {
          println("Before")
      }
  
      protected open fun requesting() {
          println("Request")
      }
  
      protected open fun after() {
          println("After")
      }
  
      fun request() {
          before()
          requesting()
          after()
      }
  }
  ```

  对默认的实现进行了定义

+ 具体实现

  ```kotlin
  class ConcreteTemplateA : AbstractTemplate() {
  
      override fun before() {
          println("Before in Concrete A")
      }
  }
  ```

  ```kotlin
  class ConcreteTemplateB : AbstractTemplate() {
  
      override fun after() {
          println("After in Concrete B")
      }
  }
  ```

  分别覆盖了抽象模板中不同的两个方法



#### 模板方法模式的一般实现方式的测试调用

```kotlin
ConcreteTemplateA().request()
ConcreteTemplateB().request()
```

结果如下

> Before in Concrete A
>
> Request
>
> After
>
> ------
>
> Before
>
> Request
>
> After in Concrete B



### 模板方法模式使用 Kotlin 高阶函数的实现方式

> [高阶函数](https://www.kotlincn.net/docs/reference/lambdas.html#%E9%AB%98%E9%98%B6%E5%87%BD%E6%95%B0) - Kotlin 语言中文站

+ 抽象模板

  模板中还是有三个默认的方法，但是用于业务的算法方法已经更改为具有默认参数的具名高阶函数

  ```kotlin
  class Template {
  
      private fun before() {
          println("Before")
      }
  
      private fun requesting() {
          println("Request")
      }
  
      private fun after() {
          println("After")
      }
  
      fun request(
          before: () -> Unit = ::before,
          requesting: () -> Unit = ::requesting,
          after: () -> Unit = ::after,
      ) {
          before()
          requesting()
          after()
      }
  }
  ```

+ 具体实现

  为配合高阶方法，具体实现不再使用类定义，而是全局方法

  ```kotlin
  fun candidateMethodA() {
      println("In Candidate A")
  }
  
  fun candidateMethodB() {
      println("In Candidate B")
  }
  
  fun candidateMethodC() {
      println("In Candidate B")
  }
  ```



#### 模板方法模式使用 Kotlin 高阶函数的实现方式的测试调用

```kotlin
Template().request(after = ::candidateMethodB)
```

结果如下

> Before
>
> Request
>
> In Candidate B

可见`after`方法已被覆盖

当然此处我们也可以传入自定义的 lambda 参数

```kotlin
Template().request(requesting = { println("Customize Requesting") })
```

结果如下

> Before
>
> Customize Requesting
>
> After



>  使用 Kotlin 高阶函数能够避免很多一般实现时带来的缺点



## 模板方法模式的优缺点

### 模板方法模式的优点

+ 将相同的逻辑放到抽象模板中，提高复用性
+ 将不同的逻辑分离到具体实现中，提高可扩展性
+ 符合开闭原则



### 模板方法模式的缺点

> Kotlin 高阶函数实现均无以下缺点

+ 每一个不同的逻辑都需要一个子类，导致类数量增加
+ 增加系统复杂性
+ 继承关系的缺点：父类添加抽象方法，子类都需更改