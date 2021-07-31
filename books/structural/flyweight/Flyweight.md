# 享元模式

> 享元模式是对象池的一种实现，避免不停的创建销毁多个对象，消耗性能

***

## 享元模式的应用场景

+ 在系统底层开发中解决系统性能问题
+ 系统中有大量相似对象，需要缓存池的情况



## 享元模式的组成角色

+ 抽象享元角色

  享元对象的基类或接口，同时定义对象外部状态和内部状态的接口或实现

  ```kotlin
  interface AbstractFlyweight {
      fun request()
  }
  ```

+ 具体享元角色

  实现抽象角色定义的方案。具体享元角色内部的状态处理应与环境无关

  ```kotlin
  class ConcreteFlyweight(val name: String) : AbstractFlyweight {
  
      override fun request() {
          println("$name $this")
      }
  }
  ```

+ 享元工厂

  负责创建和维护享元对象的工厂类

  ```kotlin
  object FlyweightFactory {
  
      private val pool = HashMap<String, AbstractFlyweight>()
  
      operator fun get(name: String): AbstractFlyweight {
          if (name !in pool) pool[name] = ConcreteFlyweight(name)
          return pool[name]!!
      }
  }
  ```

  

## 享元模式的测试调用

```kotlin
val a = FlyweightFactory["A"]
val b = FlyweightFactory["B"]
a.request()
b.request()
```

结果如下

> A ConcreteFlyweight@3f923a5d
>
> B ConcreteFlyweight@746c8da5



## 享元模式的优缺点

### 享元模式的优点

+ 减少对象的创建，降低内存中对象的数量，减少内存开销

### 享元模式的缺点

+ 可能带来线程安全问题
+ 使系统逻辑更复杂