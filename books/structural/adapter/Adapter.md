# 适配器模式
> 适配器模式可以将一个类的接口变成客户所期望的另一种接口，从而使原本因接口不匹配而导致无法在一起使用的两个类能够一起使用
***
## 适配器模式的组成

+ 目标角色

  也就是我们期望调用的方法需要的接口

  ```kotlin
  interface Target {
      fun request()
  }
  ```

+ 源角色

  系统中已经拥有的角色，但不满足于目标接口无法调用

  ```kotlin
  open class Adaptee {
      fun requestAdaptee() {
          println("Requested in Adaptee")
      }
  }
  ```

+ 适配器

  将源角色转换为目标角色，使其满足期望调用方法的需求

> 为方便理解，此例实现一个`Target`类作为期望调用方法
>
> ```kotlin
> object Target {
>     operator fun invoke(target: TargetInterface) {
>         target.request()
>     }
> }
> ```



## 适配器模式的实现

### 类适配器

> 类适配器作为目标接口的实现类，继承了已有方法的同时将自身转化为目标接口

```kotlin
class ClassAdapter: Adaptee(), TargetInterface {
    override fun request() {
        println("Requested in Adapter")
        super.requestAdaptee()
    }
}
```

类适配器调用方式：

```kotlin
Target(ClassAdapter())
```

由于自身同时为目标接口实现类和源角色子类，所以在调用期望方法时直接传入。结果如下

> Requested in Adapter
>
> Requested in Adaptee



### 对象适配器

> 对象适配器需要传入期望转换的对象，而适配器本身还是作为目标接口实现类使用

```kotlin
class ObjectAdapter(private val adaptee: Adaptee): TargetInterface {
    override fun request() {
        println("Requested in Adapter")
        adaptee.requestAdaptee()
    }
}
```

调用时相比类适配器需要传入被转换的对象，耦合度降低

```kotlin
Target(ObjectAdapter(Adaptee()))
```

结果如下

> Requested in Adapter
>
> 
>
> Requested in Adaptee



### 接口适配器

> 接口适配器即在对象适配器的目标接口和转换器之间添加一个抽象类，抽象类实现接口，而转换器继承抽象类

> 多用于目标接口方法过多，而适配器不需要实现这么多方法的情况
>
> 多余的方法在抽象类中空实现即可

> 此实现较为简单，故不再赘述



## 适配器模式的优缺点

### 适配器模式的优点

+ 提高类的透明性和复用性，但现有的类不需要改变代码
+ 对象适配器耦合性低，适配器类和源类解耦
+ 多数情况下符合开闭原则

### 适配器模式的缺点

+ 适配器编写需要考虑全部业务场景，可能增加系统复杂性
+ 降低代码可读性，适配器不宜过多使用