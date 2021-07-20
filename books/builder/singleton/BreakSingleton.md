# 破坏单例模式

使用序列化以及反射破坏单例模式，并实例化出一个新的对象

***

## 序列化破坏方式

序列化破坏的前提是单例对象的类实现了`Serializable`接口，反向可知防御序列化最好的方式就是不实现`Serializable`接口

这里默认所使用的单例类已经实现了`Serializable`接口

### 破坏

```kotlin
private fun <T> attack(origin: T): T {
    val out = ByteArrayOutputStream()
    ObjectOutputStream(out).writeObject(origin)
    val new = ObjectInputStream(ByteArrayInputStream(out.toByteArray())).readObject() as T
    println(origin == new)
    return new
}
```

将单例对象序列化为字节流再反序列化为对象，并强转为单例类的类型

此时将之前所有的不同的单例实现方式的单例对象传入此方法，`println(origin == new)`出现了以下结果

|     实现方式      | 比对结果 |
| :---------------: | :------: |
|      饿汉式       | `false`  |
|      懒汉式       | `false`  |
| Kotlin Object对象 | `false`  |
|      枚举类       |  `true`  |

可知除了枚举类以外的任何方式在序列化面前表现的不堪一击，轻松就实例化出了一个新的对象

那么我们应该怎么避免被序列化攻击呢？



### 防御

防御序列化破坏单例的方式很简单，你只需要添加一个方法

```kotlin
private fun readResolve(): Any = instance
```

在反序列化时，会检查类中是否有此方法，如果有的话直接将方法返回的对象作为反序列化对象返回

此时再进行测试可以发现，所有实现方式的比对结果都是`true`



## 反射破坏

反射破坏的基本原理是利用反射获取构造器对象，并更改可见性，最后进行实例化

由于Kotlin也有自己的一套反射API，所有我们区分Java和Kotlin的反射来讨论

### Kotlin反射

#### 破坏

> Kotlin反射属于扩展包，需要手动添加

```kotlin
private fun <T> reflect(origin: T): T {
    val clazz = origin::class
    val constructor = clazz.constructors.first()
    constructor.isAccessible = true
    val new = constructor.call()
    println(origin == new)
    return new
}
```

此时将之前所有的不同的单例实现方式的单例对象传入此方法，`println(origin == new)`出现了以下结果

|     实现方式      |  比对结果  |
| :---------------: | :--------: |
|      饿汉式       |  `false`   |
|      懒汉式       |  `false`   |
| Kotlin Object对象 | **不适用** |
|      枚举类       | **不适用** |

可以发现，只有一般的类可以使用此方法，而Kotlin Object对象和枚举类都无法使用此方法攻破单例模式。

#### 分析

##### Kotlin Object对象

先来看Kotlin Object对象，其不适用此方法的原因我们可以通过调取`reflect`方法的报错可得`List is empty.`

回到方法我们可以发现整个方法涉及`List`只有`clazz.constructors`。

通过测试：

```kotlin
println(Hungry::class.constructors.size) // 0
```

可知，Kotlin Object方法在Kotlin反射中是没有构造参数的，所以我们无法实例化出一个新的对象破坏单例模式。



##### 枚举类

同样通过枚举类传入方法的报错可得`Cannot reflectively create enum objects`

我们无法通过反射来创建一个新的枚举类对象，自然反射的方式就失败了

[Java Language Specification (§8.9)](http://docs.oracle.com/javase/specs/jls/se7/html/jls-8.html#jls-8.9):

>It is a compile-time error to attempt to explicitly instantiate an enum type (§15.9.1). The final clone method in Enum ensures that enum constants can never be cloned, and the special treatment by the serialization mechanism ensures that duplicate instances are never created as a result of deserialization. Reflective instantiation of enum types is prohibited. Together, these four things ensure that no instances of an enum type exist beyond those defined by the enum constants.



### Java反射

#### 破坏

```kotlin
private fun <T> reflect(origin: T): T {
    val clazz = origin::class.java
    val constructors = clazz.getDeclaredConstructor()
    constructors.isAccessible = true
    val new = constructors.newInstance() as T
    println(origin = new)
    return new
}
```

此时将之前所有的不同的单例实现方式的单例对象传入此方法，`println(origin == new)`出现了以下结果

|     实现方式      |  比对结果  |
| :---------------: | :--------: |
|      饿汉式       |  `false`   |
|      懒汉式       |  `false`   |
| Kotlin Object对象 |  `false`   |
|      枚举类       | **不适用** |

可以发现，在Java反射中，只有枚举类依然不适用本方法。其他的都成功实例化出了对象



#### 分析

##### Kotlin Object对象为什么在Java反射中失效了

因为从Java反射的层面上来讲并不存在什么Object单例对象，Object对象仅存在于Kotlin中，Kotlin为避免Object类被破坏做的事情也仅限在Kotlin的层面上

所以从Java反射的角度讲，Object对象与一般的饿汉式单例类并没有什么区别，所以Java反射很容易的获取到了其构造器并进行了实例化

见[Kotlin单例类](Hungry.md)



##### 枚举类

它依然无法实例化，无论你怎么反射



### 防御

>  **需要注意的是，反射破坏无法完全防御，除非用枚举**

#### 尝试着防御

```kotlin
class Hungry {
    companion object {
        private var flag = false

        val instance = Hungry()
    }

    init {
        synchronized(Hungry::class.java) {
            if (flag) throw RuntimeException("禁止破坏单例")
            flag = true
        }
    }
}
```

在构造器内添加一个检查标志`flag`的`if`操作，第一次实例化时将`flag`改为`true`，这样在第二次实例化时就会抛出`RuntimeException`

但是这样是否就能像序列化那样完全防住破坏呢？事实上，这是不可能的



#### 破坏防御

即使使用了一个标志`flag`来避免第二次实例化，但是别忘了反射依然可以随意更改`flag`的值

```kotlin
val origin = Hungry.instance
val clazz = Hungry::class

// 更改flag的值
val flag = clazz.companionObject?.memberProperties?.first { it.name == "flag" }
if (flag is KMutableProperty<*>) {
    flag.isAccessible = true
    flag.setter.call(clazz.companionObjectInstance, false)
}

// 获取构造器，实例化对象
val constructor = clazz.constructors.first()
constructor.isAccessible = true
val new = constructor.call()

println(origin == new) // false
```



值得注意的是，如果是懒汉式采用标志来防御反射，而且第一次实例化就是反射调用的，那么未进行初始化的`instance`变量如果不再次反射修改，将永久性出现异常

一般类实现的单例是无法防御反射的破坏的，**有且仅有枚举类能够防御一切破坏**

