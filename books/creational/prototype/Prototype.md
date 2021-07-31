# 原型模式

> 指定原型类型，并且通过复制原型创建新的对象

***

## 原型模式实现方式

>使用一个`Flag`类区分深浅克隆
>
>```kotl
>class Flag
>```

### 浅克隆

浅克隆指仅克隆出新的对象，而此克隆对象内部属性与被克隆对象内部属性为同一个对象

#### 一般实现

##### 一般实现组成

+ 抽象原型

  用于规定用于克隆的接口

  ```kotlin
  interface Clone<T> {
      fun clone(): T
  }
  ```

+ 具体原型

  被克隆的对象

  ```kotlin
  class General(val flag: Flag): Clone<General> {
      override fun clone(): General {
          return General(flag)
      }
  }
  ```

##### 调用一般实现

```kotlin
val flag = Flag()
val origin = General(flag)
val new = origin.clone()
println(origin == new) // false
println(origin.flag == new.flag) // true
```

可知`origin`和`new`非同一个对象，而内部的`flag`属性对象却是相同的



#### Object类中的`clone`方法

> `clone`方法来自Java `Object`超类，Kotlin的超类`Any`中并未定义`clone`

> 使用`clone`方法需要实现接口`Cloneable`

```kotlin
class Shallow: Cloneable {

    val flag = Flag()

    public override fun clone(): Shallow {
        return super.clone() as Shallow
    }
}
```

测试方式如下

```kotlin
val origin = Shallow()
val clone = origin.clone()
println(origin == clone) // false
println(origin.flag == clone.flag) // true
```

结果中，`flag`属性依然是相同的，所以`clone`方法也仅仅是浅克隆



### 使用序列化实现深克隆

> 使用序列化需要将标志类`Flag`也改为可序列化
>
> ```kotlin
> class Flag: Serializable
> ```

```kotlin
class Deep: Cloneable, Serializable {

    val flag = Flag()

    public override fun clone(): Deep {
        val out = ByteArrayOutputStream()
        ObjectOutputStream(out).use { it.writeObject(this) }
        return ObjectInputStream(ByteArrayInputStream(out.toByteArray())).use { it.readObject() } as Deep
    }
}
```

> use方法：[习惯用法 - Java7的try with resources](https://www.kotlincn.net/docs/reference/idioms.html#java-7-%E7%9A%84-try-with-resources)

测试方式如下

```kotlin
val origin = Deep()
val clone = origin.clone()
println(origin == clone) // false
println(origin.flag == clone.flag) // false
```

可知，深克隆不仅复制了对象本体，对象内部属性也进行了一次克隆



## 原型模式优缺点

### 优点

+ 使用自带的内存二进制流复制，性能上比直接实例化对象更优良
+ 可以使用深克隆保持对象状态

### 缺点

+ 克隆需要`clone`方法，对已经类型进行改造时，违背了开闭原则
+ 对于嵌套较深的对象，为实现深克隆，每一层嵌套的对象都需要支持序列化
