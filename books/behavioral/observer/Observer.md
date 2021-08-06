# 观察者模式

> 指一个对象被多个观察者对象同时监听，被监听对象状态变化时，所有监听它的观察者对象都会收到通知

***

## 应用场景

+ 一个抽象模型包括两方面内容，并且一方面依赖另一方面
+ 一个或多个对象变化依赖另一个对象变化
+ 广播机制
+ 链式触发机制



## 组成角色

+ 抽象发布者

  被观察的对象的抽象，维护着观察者的集合

+ 具体发布者

  具体的发布者，内部同时还维护着被观察的属性

+ 抽象观察者

  观察者对象的抽象，定义收到通知后的抽象行为

+ 具体观察者

  具体定义收到通知后的行为



## 实现方式

### 一般实现方式

> 一般实现不依赖任何语言独有特性和 API

> 本例中所有角色均采用泛型的方式提高适用性

+ 抽象发布者

  ```kotlin
  abstract class AbstractObservable<E> {
      abstract val observers: MutableSet<AbstractObserver<E>>
  }
  ```

+ 具体发布者

  > Kotlin 中的`Set`为不可变集合，而`MutableSet`为可变集合

  >  `Set`为无序集合，所以通知不一定按顺序
  >
  > 如需按顺序优先级通知，应使用`List`

  >  泛型无法在非内联方法内实例化，所以`value`值采用可空类型并赋予初始值`null`

  ```kotlin
  class Observable<E> : AbstractObservable<E>() {
  
      override val observers: MutableSet<AbstractObserver<E>> = HashSet()
  
      var value: E? = null
          set(value) {
              value?.let {
                  observers.forEach { it.update(value) }
                  field = value
              }
          }
  }
  ```

+ 抽象观察者

  ```kotlin
  interface AbstractObserver<E> {
      fun update(value: E)
  }
  ```

+ 具体观察者

  ```kotlin
  class ObserverA<E> : AbstractObserver<E> {
  
      override fun update(value: E) {
          println("A: Value has Changed to $value")
      }
  }
  ```

  ```kotlin
  class ObserverB<E> : AbstractObserver<E> {
  
      override fun update(value: E) {
          println("B: Value has Changed to $value")
      }
  }
  ```

测试代码如下

```kotlin
val observer = Observable<Int>()
observer.observers += arrayOf(ObserverA(), ObserverB())
observer.value = 666
```

结果如下

>B: Value has Changed to 666
>
>A: Value has Changed to 666



### Java 标准库 API 实现方式

> Java 标准库中提供了`java.util.Observable`类和`java.util.Obsever`接口

> [Observable](https://docs.oracle.com/javase/8/docs/api/java/util/Observable.html) - Java docs
>
> [Observer](https://docs.oracle.com/javase/8/docs/api/java/util/Observer.html) - Java docs

+ 抽象发布者

  `java.util.Observable`类

+ 具体发布者

  >  `java.util.Observable`可以直接作为发布者实例化，但本例对其进行了一定封装，使外部调用更简洁

  >  `java.util.Observable`自动维护了观察者集合，使用的是`Vector`

  > `setChanged`方法表示允许下一次更改并通知

  > `notifyObservers`方法被调用后自动通知所有观察者

  ```kotlin
  class Observable : java.util.Observable() {
  
      init {
          addObserver(ObserverA())
          addObserver(ObserverB())
      }
  
      var value: Int = 0
          set(value) {
              field = value
              setChanged()
              notifyObservers(value)
          }
  }
  ```

+ 抽象观察者

  `java.util.Observer`接口

+ 具体观察者

  >`update`方法中两个参数分别为`java.util.Observable`实例和被通知的值

  ```kotlin
  class ObserverA : Observer {
  
      override fun update(o: Observable?, arg: Any?) {
          println("A: Value has Changed to $arg")
      }
  }
  ```

  ```kotlin
  class ObserverB : Observer {
  
      override fun update(o: Observable?, arg: Any?) {
          println("B: Value has Changed to $arg")
      }
  }
  ```

测试代码如下

```kotlin
Observable().value = 666
```

由于对发布者进行了高度封装，测试调用变得较为简洁

结果如下

> B: Value has Changed to 666
>
> A: Value has Changed to 666



### Kotlin 委托实现

> 观察者角色代码均与一般实现相同

#### `ObservableProperty` 抽象类

+ 具体发布者

  > `beforeChange`在更改值之前被调用，若返回值为`false`，则不进行此次更改
  >
  > `afterChange`在值被更改后调用

  ```kotlin
  class Observable {
  
      val observers = HashSet<AbstractObserver<Int>>()
  
      var valueByObservableProperty by object : ObservableProperty<Int>(0) {
          override fun beforeChange(property: KProperty<*>, oldValue: Int, newValue: Int): Boolean = newValue > 0
  
          override fun afterChange(property: KProperty<*>, oldValue: Int, newValue: Int) {
              observers.forEach { it.update(newValue) }
          }
      }
  }
  ```

测试代码如下

```kotlin
Observable().valueByObservableProperty = 666
```

结果如下

> A: Value has Changed to 666
>
> B: Value has Changed to 666



#### 委托标准库 `vetoable` 和 `observable`

> `vetoable`方法定义如下
>
> ```kotlin
> public inline fun <T> vetoable(initialValue: T, crossinline onChange: (property: KProperty<*>, oldValue: T, newValue: T) -> Boolean):ReadWriteProperty<Any?, T> =
>      object : ObservableProperty<T>(initialValue) {
>          override fun beforeChange(property: KProperty<*>, oldValue: T, newValue: T): Boolean = onChange(property, oldValue, newValue)
>      }
> ```
>
> `observable`方法定义如下
>
> ```kotlin
> public inline fun <T> observable(initialValue: T, crossinline onChange: (property: KProperty<*>, oldValue: T, newValue: T) -> Unit):ReadWriteProperty<Any?, T> =
>         object : ObservableProperty<T>(initialValue) {
>             override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) = onChange(property, oldValue, newValue)
>         }
> ```
>
> 可见`vatoble`方法和`observable`方法只是分别实现了`ObservableProperty` 抽象类中的`beforeChange`方法和`afterChange`方法而已

具体代码如下

```kotlin
var valueByVetoes by Delegates.vetoable(0) { _, _, newValue ->
    newValue > 0
}
```

```kotlin
var valueByObserver by Delegates.observable(0) { _, _, newValue ->
    observers.forEach { it.update(newValue) }
}
```

效果与`ObservableProperty` 抽象类实现方式相同，故此处不再赘述



## 优缺点

+ **优点**
  + 松耦合符合依赖倒置原则
  + 分离了观察者和被观察者，并建立了一套触发机制，使得数据变化可以相应到多个表示层
  + 实现了一对多的通信机制
+ **缺点**
  + 观察者较多时性能较差
  + 事件通知为线性关系，如果一个观察者中处理较久会影响后续观察者
  + 如果存在依赖循环会导致系统崩溃

***
> 以下链接请在 GitHub 中使用
>
> GitHub Pages请点击页面最上方的 "View on GitHub"
>
> + [具体业务源代码](../../../src/main/kotlin/club/gargantua7/design_patterns/behavioral/observer)
> + [测试用例源代码](../../../src/test/kotlin/club/gargantua7/design_patterns/behavioral/observer)
