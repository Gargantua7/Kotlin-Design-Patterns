# 装饰器模式

> 装饰器模式指不改变原有对象的基础上，动态地给一个对象添加一些额外的职责

***

## 装饰器模式的适用场景

+ 扩展一个类的功能，或者给一个类添加附加职责
+ 动态地给一个对象添加功能，这些功能还可以动态的被撤销
+ 需要为一批平行的兄弟类镜像改装或加装功能



## 装饰器模式的组成角色

+ 抽象组件

  一个抽象类或者接口，作为被装饰类的原始对象，规定被装饰类对象的行为

  ```kotlin
  interface AbstractComponent {
      fun request()
  }
  ```

+ 具体组件

  实现/继承抽象组件的一个具体对象，即被装饰对象

  ```kotlin
  class ConcreteComponent : AbstractComponent{
  
      override fun request() {
          println("ConcreteComponent is requested")
      }
  }
  ```

+ 抽象装饰器

  装饰具体组件的抽象装饰器，其内部有一个属性指向具体组件

  若系统内只有一个装饰器大可以省略此类

  ```kotlin
  abstract class Decorator(protected open val component: AbstractComponent) : AbstractComponent {
  
      override fun request() {
          component.request()
      }
  }
  ```

+ 具体装饰器

  抽象装饰器的具体实现，理论上每个具体的装饰器都扩展了抽象组件的的不同功能

  ```kotlin
  class ConcreteDecoratorA(override val component: AbstractComponent) : Decorator(component) {
  
      override fun request() {
          println("ConcreteDecorator A is Requested")
          super.request()
      }
  }
  
  class ConcreteDecoratorB(override val component: AbstractComponent) : Decorator(component) {
  
      override fun request() {
          println("ConcreteDecorator B is Requested")
          super.request()
      }
  }
  ```

  

## 装饰器模式的测试调用

```kotlin
val concreteComponent = ConcreteComponent()
ConcreteDecoratorA(concreteComponent).request()
ConcreteDecoratorB(concreteComponent).request()
```

先获得具体组件的也就是被装饰对象，将被装饰对象传入装饰器类中，调用装饰器的方法即可

结果如下

>ConcreteDecorator A is Requested
>
>ConcreteComponent is requested
>
>ConcreteDecorator B is Requested
>
>ConcreteComponent is requested



装饰器模式的特性在于可以多层组合

```kotlin
ConcreteDecoratorA(ConcreteDecoratorB(ConcreteComponent())).request()
```

结果如下

> ConcreteDecorator A is Requested
>
> ConcreteDecorator B is Requested
>
> ConcreteComponent is requested



## 装饰器模式和代理模式的区别

装饰器模式在于增加被装饰对象的功能

而代理模式主要在于保护被代理对象



## 装饰器模式的优缺点

### 装饰器模式的优点

+ 装饰器模式是继承的补充，且比继承灵活，在不改变原有对象的情况下，动态地给一个对象扩展功能
+ 通过使用不同的装饰器组合可以实现不同的效果
+ 装饰器模式完全遵守开闭原则



### 装饰器模式的缺点

+ 代码增多导致程序复杂性增加
+ 在多层装饰时会变得异常复杂