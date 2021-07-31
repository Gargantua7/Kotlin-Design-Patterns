# 懒汉式单例模式

指全局的单例实例在**第一次被使用时**构建

***

## 一般实现

```kotlin
class Lazy private constructor() {
    companion object var instance: Lazy? = null
        private set
        get() {
            field ?: run { instance = Lazy() }
            return field
        }
}
```

值得注意的是，懒汉式若什么都不做的话，是**线程不安全**的！

但是由于是惰性加载的，对内存利用率相比较饿汉式较好



## 线程安全实现

```kotlin
class Lazy private constructor() {
    companion object var instance: Lazy? = null
        private set
        @Synchronized get() {
            field ?: run { instance = Lazy() }
            return field
        }
}
```

`@Synchronzied`修饰方法时是等价于Java的`synchronize`关键字的

代表在每次访问变量`instance`时都会对此类上锁，这虽然保证了多线程访问时的线程安全，但由于每次访问时都会上锁，导致其他线程等待。这样的时间效率是非常低的。



## 双重检查锁实现

```kotlin
class Lazy private constructor() {
    companion object @Volatile var instance: Lazy? = null
        private set
        get() {
            field ?: run {
                synchronized(Lazy::class.java) {
                    field ?: run { field = Lazy() }
                }
            }
            return field
        }
}
```

双重检查锁会首先检查属性`instance`是否为空，如果是空的话才对类上锁。也就是说整一个类只会在被锁一次。保证了线程安全的同时，优化了时间效率

`synchronize`方法是Kotlin的一个内联方法，意义等同于Java的`synchronize`代码块。以下为Kotlin `synchronize`方法定义代码

```kotlin
public actual inline fun <R> synchronized(lock: Any, block: () -> R): R
```

双重检查锁的第二重检查是为了使在第一次上锁是等待的线程不会再重新给属性赋值，所以重新检查一次是否为空。正常情况下仅拿到锁的线程会在此处判定结果为空并初始化`instance`

`@Volatile`注解等价于Java中的`volatile`关键字，其目的是避免JVM对代码进行优化和重排，导致方法返回空指针



## 静态内部类实现

```kotlin
class Lazy private constructor() {
    object Inner() {
        val instance = Lazy()
    }

    val instance: Lazy
        get() = Inner.instance
}
```

静态内部类在被调用时才进行初始化，保证了线程安全，同时也有同样的性能



## Kotlin实现

```kotlin
class Lazy private constructor() {
    companion object val instance by lazy { Lazy() }
}
```

将属性`instance`委托给了`lazy`方法，以下为`lazy`方法定义

```kotlin
public actual fun <T> lazy(initializer: () -> T): Lazy<T> = SynchronizedLazyImpl(initializer)
```

可以发现`lazy`方法返回值为`SynchronizedLazyImpl`对象，我们再次来到`SynchronizedLazyImpl`类的定义，我们主要关注于以下一段代码

```kotlin
private class SynchronizedLazyImpl<out T>(initializer: () -> T, lock: Any? = null) : Lazy<T>, Serializable {
    private var initializer: (() -> T)? = initializer
    @Volatile private var _value: Any? = UNINITIALIZED_VALUE
    // final field is required to enable safe publication of constructed instance
    private val lock = lock ?: this

    override val value: T
        get() {
            val _v1 = _value
            if (_v1 !== UNINITIALIZED_VALUE) {
                @Suppress("UNCHECKED_CAST")
                return _v1 as T
            }

            return synchronized(lock) {
                val _v2 = _value
                if (_v2 !== UNINITIALIZED_VALUE) {
                    @Suppress("UNCHECKED_CAST") (_v2 as T)
                } else {
                    val typedValue = initializer!!()
                    _value = typedValue
                    initializer = null
                    typedValue
                }
            }
        }
}
```

可以发现，这段代码其实与上面的双重锁检查锁的原理是一模一样的。其中`value`即为我们的实例对象。`lazy`方法返回的`Lazy`对象也可以在字节码反编译中发现Kotlin更改了`getInstance`方法使`getter`获取到值为实例对象

```java
@NotNull
public final Lazy getInstance() {
     kotlin.Lazy var1 = instance$delegate;
     Object var3 = null;
     boolean var4 = false;
     return (Lazy)var1.getValue();
  }
```

所以Kotlin委托给`lazy`方法惰性加载的单例模式也是**线程安全**并且拥有同样效率的

