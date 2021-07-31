#  饿汉式单例模式

指全局的单例实例**在类装载时**构建

***

## 饿汉式的一般实现

```kotlin
class Hungry private constructor() {
    companion object val instance = Hungry()
}
```

等价于Java代码

```Java
public final class Hungry {
    private Hungry() {}
    
    private static final Hungry instance = new Hungry();

    
    public static Hungry getInstance() {
        return instance;
    }
}
```

在Kotlin中的属性是自动添加getter/setter的，由于我们这里`instance`定义为不可变，所以可以直接对外开放

饿汉模式的一般实现中的实例是直接赋值给了属性，线程安全但是类加载时就已初始化，浪费内存



## 静态代码块实现

```kotlin
class Hungry private constructor() {
    companion object {
        var instance: Hungry
            private set
        init {
            instance = Hungry()
        }
    }
}
```

等价Java代码

```java
public final class Hungry {
    private Hungry() {}
    
    private static Hungry instance;
    
    static {
        instance = new Hungry();
    }
    
    public static Hungry getInstance() {
        return instance;
    }
}
```

与一般实现差不多



## Kotlin单例类

```kotlin
Object Hungry
```

Kotlin自带的单例模式，其底层实现为饿汉式

将`Object`类字节码反编译后可以得到以下Java代码

```java
public final class Hungry {
   @NotNull
   public static final Hungry INSTANCE;

   private Hungry() {
   }

   static {
      Hungry var0 = new Hungry();
      INSTANCE = var0;
   }
}
```

> 对象声明的初始化过程是线程安全的并且在首次访问时进行。
>
> ------ [对象表达式与对象声明](https://www.kotlincn.net/docs/reference/object-declarations.html#%E5%AF%B9%E8%B1%A1%E5%A3%B0%E6%98%8E) - Kotlin语言中文站



## 枚举类实现

```kotlin
enum class Hungry {
    INSTANCE
}
```

枚举类中的对象可以绝对保证全局唯一性，其本质是懒汉式

这是一种你可以认为是绝对安全的实现方式
