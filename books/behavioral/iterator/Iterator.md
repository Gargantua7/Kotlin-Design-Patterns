# 迭代器模式

> 迭代器模式提供一种按顺序访问集合/容器对象元素的方法，而无需暴露集合内部表示

***

## 应用场景

+ 访问一个集合对象内容但不想暴露内部表示
+ 为遍历不同集合提供的一个统一访问接口



## 组成角色

+ 抽象迭代器

  定义访问和遍历对象的接口

  > JDK 以及 Kotlin 标准库中包含的 `Iterator` 接口，则可以直接使用

  > 以下为 Kotlin 标准库的 `Iterator` 接口

  > Kotlin 中 `Iterator` 接口不包含`remove`方法，因为 Kotlin 区分可变集合和不可变集合，如需可变，应使用`MutableIterator`接口

  ```kotlin
  public interface Iterator<out T> {
      /**
       * Returns the next element in the iteration.
       */
      public operator fun next(): T
  
      /**
       * Returns `true` if the iteration has more elements.
       */
      public operator fun hasNext(): Boolean
  }
  ```

+ 抽象容器

  定义提供迭代器的接口

  ```kotlin
  interface AbstractAggregate<E> {
  
      fun add(element: E)
  
      fun remove(element: E)
  
      operator fun iterator(): Iterator<E>
  }
  ```

+ 具体迭代器

  提供具体元素遍历行为

+ 具体容器

  提供具体迭代器对象

  > 本例仿照`ArrayList`类，迭代器类为私有内部类形式定义

  > `Iterable`接口将其标记为可以顺序访问此类中的元素，可以使用`for..each`以及其他 Kotlin 集合迭代器方法

  > 不过需要注意的是，Kotlin 的 `for..in` 语法并不依赖此接口，即使未实现此接口也可使用，其真正依赖为运算符重载`operator`修饰符
  >
  > 若想正常使用，其迭代器获取方法`iterator`和迭代器内部的方法`next`和`hasNext`均应使用运算符重载
  >
  > 同时由于 Kotlin 集合迭代器方法内部实现均使用`for..in`语法，则若想使用 Kotlin 集合迭代器方法需要在能正常使用`for..in`的前提下实现`Iterable`接口

  ```kotlin
  class Aggregate<E> : AbstractAggregate<E>, Iterable<E> {
  
      private val list = ArrayList<E>()
  
      override fun add(element: E) {
          list.add(element)
      }
  
      override fun remove(element: E) {
          list.remove(element)
      }
  
      override operator fun iterator(): Iterator<E> = Itr()
  
      private inner class Itr : Iterator<E> {
  
          private var index = 0
  
          override fun next(): E = list[index++]
  
          override fun hasNext(): Boolean = index < list.size
      }
  }
  ```

  

## 测试调用

```kotlin
val list = Aggregate<Int>()
list.add(1)
list.add(2)
for (i in list) print(i)
println()
list.forEach { print("$it ") }
println()
println(list.fold(0) { sum, it -> sum + it })
```

> `fold`方法为遍历时从初始值开始累加值，并从左到右应用操作到当前累加器值和每个元素

结果如下

> 1 2 
>
> ***
>
> 1 2 
>
> ***
>
> 3



## 优缺点

+ **优点**
  + 可以多态迭代
  + 简化集合接口
  + 元素迭代功能多样化
  + 解耦迭代与集合
+ **缺点**
  + 对于简单的遍历，完全手写迭代器比较繁琐，一般直接使用 API 标准库即可

***
> 以下链接请在 GitHub 中使用
>
> GitHub Pages请点击页面最上方的 "View on GitHub"
>
> + [具体业务源代码](../../../src/main/kotlin/club/gargantua7/design_patterns/behavioral/iterator)
> + [测试用例源代码](../../../src/test/kotlin/club/gargantua7/design_patterns/behavioral/iterator)
