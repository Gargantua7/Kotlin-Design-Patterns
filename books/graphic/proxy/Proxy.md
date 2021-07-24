# 代理模式

> 为其他对象提供的一种代理，以控制这个对象的访问

***

## 代理模式组成

+ 抽象角色

  真实角色和代理角色都共同需要实现的接口方法，可以是接口可以是抽象类

+ 真实角色

  被代理类，代理模式中的真正执行业务的对象

+ 代理角色

  代理类，由于实现了抽象类，内部存在所有被代理类的引用，具备对被代理类完全的代理权。一般会在代理对象执行业务代理前后执行一些逻辑

## 代理模式的实现方式

> 代理模式分为**静态代理**实现和**动态代理**实现

### 静态代理实现

#### 一般实现

##### 一般实现组成

+ 抽象角色

  ```kotlin
  interface Subject {
      fun request()
  }
  ```

  接口中仅有一个方法用于测试即可

+ 真实角色

  ```kotlin
  class RealSubject : Subject {
      override fun request() {
          println("Real Subject is requesting")
      }
  }
  ```

  被代理类中实现接口的方法并模拟业务执行

+ 代理角色

  ```kotlin
  class Proxy(private val subject: Subject) : Subject {
      override fun request() {
          before()
          subject.request()
          after()
      }
      private fun before() {
          println("called before request")
      }
      private fun after() {
          println("called after request")
      }
  }
  ```

  代理类中调用真实被代理类的实际业务逻辑，并且在业务逻辑前后模拟通知



##### 一般实现测试

```kotlin
Proxy(RealSubject()).request()
```

直接调用`Proxy`类并传入需要被代理的`RealSubject`对象，调用实际业务方法即可

运行结果：

> called before request
> 
> **Real Subject is requesting**
> 
> called after request

可见在实际业务逻辑执行前后进行了通知



#### Kotlin 委托

> [委托](https://www.kotlincn.net/docs/reference/delegation.html) - Kotlin 语言中文站
>
> Kotlin 委托能够 0 样板代码的实现静态代理

##### Kotlin 委托组成

+ 抽象角色

  *与一般实现一致*

+ 真实角色

  *与一般实现一致*

+ 代理角色

  ```kotlin
  class KotlinDelegate(private val subject: Subject) : Subject by subject
  ```

  可以将此类定义认为是`: Subject`（实现`Subject`接口），再`by subject`（将`subject`作为代理对象）

  将 Kotlin 字节码反编译可以看到以下内容

  ```java
  public static final class KotlinDelegate implements Subject {
      private final Subject subject;
      
      public KotlinDelegate(@NotNull Subject subject) {
     		Intrinsics.checkNotNullParameter(subject, "subject");
     		super();
     		this.subject = subject;
  	}
      
       public void request() {
           this.subject.request();
        }
  }
  ```

  可见 Kotlin 将委托编译为了与静态代理原理一致的字节码

  如果需要在前后添加逻辑，则还是与静态代理一样需要自行实现接口方法

  ```kotlin
  class KotlinDelegate(private val subject: Subject) : Subject by subject {
      override fun request() {
          before()
          subject.request()
          after()
      }
      
      private fun before() {}
      private fun after() {}
  }
  ```

  

##### Kotlin 委托测试

```kotlin
KotlinDelegate(RealSubject()).request()
```

调用方法与一般静态代理方法大致相同

如果未添加前后通知则有以下结果

> **Real Subject is requesting**

添加前后通知则为以下结果

> called before request
> 
> **Real Subject is requesting**
> 
> called after request



### 动态代理实现

> 动态代理实际上是在程序执行期间动态生成代理类字节码实行代理
>
> 常用的动态代理方式有 **JDK 代理**和 **CGLIB 代理**

#### JDK 动态代理

> JDK 代理顾名思义是 JDK 中自带的一种代理方式
>
> 由`java.lang.reflect.Proxy`类提供支持

##### JDK 动态代理组成

+ 抽象角色

  *与一般实现一致*

+ 真实角色

  *与一般实现一致*

+ 代理角色

  ```kotlin
  object JDKProxy {
      operator fun invoke(subject: Subject) = Proxy.newProxyInstance(
          subject.javaClass.classLoader,
          subject.javaClass.interfaces,
      ) { _: Any, method: Method, _: Array<Any>? ->
          before()
          println("JDK Proxy is working")
          val res = method.invoke(subject)
          after()
          return@newProxyInstance res
      } as Subject
      private fun before() {
          println("called before request")
      }
      private fun after() {
          println("called after request")
      }
  }
  ```

  > [函数式（SAM）接口](https://www.kotlincn.net/docs/reference/fun-interfaces.html) - Kotlin 语言中文站

  注意此类型并不是真正的代理类，动态代理中代理类会在执行过程中动态生成

  保存动态生成的字节码文件，并反编译查看（已删除无关代码）

  ```java
  public final class $Proxy0 extends Proxy implements Subject {
      private static Method m3;
  
      public $Proxy0(InvocationHandler var1) throws  {
          super(var1);
      }
  
      public final void request() throws  {
          try {
              super.h.invoke(this, m3, (Object[])null);
          } catch (RuntimeException | Error var2) {
              throw var2;
          } catch (Throwable var3) {
              throw new UndeclaredThrowableException(var3);
          }
      }
  
      static {
          try {
              m3 = Class.forName("Subject").getMethod("request");
          } catch (NoSuchMethodException var2) {
              throw new NoSuchMethodError(var2.getMessage());
          } catch (ClassNotFoundException var3) {
              throw new NoClassDefFoundError(var3.getMessage());
          }
      }
  }
  ```

  为方便理解，同时贴上`Proxy`类的部分有关代码

  ```java
  public class Proxy implements java.io.Serializable {
      
  	/**
       * the invocation handler for this proxy instance.
       * @serial
       */
      protected InvocationHandler h;
      
      /**
       * Constructs a new {@code Proxy} instance from a subclass
       * (typically, a dynamic proxy class) with the specified value
       * for its invocation handler.
       *
       * @param  h the invocation handler for this proxy instance
       *
       * @throws NullPointerException if the given invocation handler, {@code h},
       *         is {@code null}.
       */
      protected Proxy(InvocationHandler h) {
          Objects.requireNonNull(h);
          this.h = h;
      }
  }
  ```

  再来分析动态生成`$Proxy0`的原理：

  1. 在类加载器加载`$Proxy0`类时，利用反射获取到实际执行业务的方法`request`的包装类，并赋值给成员属性
  2. 调用`Proxy.newProxyInstance()`时，返回`$Proxy0`实例。构造器内参数为`Proxy.newProxyInstance()`的第三个参数，也就是 SAM 转换出的`InvocationHandler`对象，构造器类调用`Proxy`内对应构造器，将`InvocationHandler`对象交给父类
  3. 外部调用`request()`方法时调用`InvocationHandler`对象中的`invoke()`方法，接下来执行我们自定义的代码

  同时根据`$Proxy0.request()`中的代码可知，SAM 转换的`InvocationHandler`对象的`invoke()`方法的三个参数分别是：

  1. `Any`/`Object`类型：代理类`$Proxy0`本体
  2. `Method`类型：抽象类中的被代理方法的包装类对象
  3. `Array<Any>`/`Object[]`类型：传入的参数，**无参时为`null`**。由于我们此例无参，在 Kotlin 调用中时应声明可空，并在调用`Method.invoke()`时不传入此参数或传入`null`。`$Proxy0.request()`类传入的也为`null`



#####  JDK 动态代理调用

```kotlin
JDKProxy(RealSubject()).request()
```

也是直接调用，在外部调用并没有什么区别，结果如下

> called before request
> 
> **JDK Proxy is working**
> 
> **Real Subject is requested**
> 
> called after request



#### CGLIB 动态代理

> CGLIB 位第三方库需要从外部导入：[Maven Repository](https://mvnrepository.com/artifact/cglib/cglib)

##### CGLIB 动态代理组成

> **CGLIB 动态代理不需要抽象角色，也就是抽象类或者接口**

+ 真实角色

  ```kotlin
  open class RealSubject {
      open fun request() {
          println("Real Subject is requesting")
      }
  }
  ```

  CGLIB 代理类直接继承自真实角色，因为 Kotlin 的类和方法默认为`final`最终类/最终方法不可继承，所以需要在被代理的类和方法前加上`open`关键字，否则会报错或者不执行代理

+ 代理角色

  ```kotlin
  object CGLIBProxy {
      operator fun invoke(subject: RealSubject): RealSubject {
          val enhancer = Enhancer()
          // 设置代理类父类
          enhancer.setSuperclass(RealSubject::class.java)
          // 设置回调
          enhancer.setCallback(
              MethodInterceptor { _: Any, _: Method, args: Array<Any>, method: MethodProxy ->
                  before()
                  println("CGLIB Proxy is working")
                  val res = method.invoke(subject, args)
                  after()
                  return@MethodInterceptor res
          })
          // 创建代理类并返回
          return enhancer.create() as RealSubject
      }
      private fun before() {
          println("called before request")
      }
      private fun after() {
          println("called after request")
      }
  }
  ```

  与 JDK 动态代理一致，此类也并不是真正的代理类，还是查看动态代理的字节码（删除无关代码）

  ```java
  public class RealSubject$$EnhancerByCGLIB$$5a7086f2 extends RealSubject implements Factory {
      private MethodInterceptor CGLIB$CALLBACK_0;
      private static final Method CGLIB$request$0$Method;
      private static final MethodProxy CGLIB$request$0$Proxy;
      private static final Object[] CGLIB$emptyArgs;
      
      public RealSubject$$EnhancerByCGLIB$$5a7086f2() {
          CGLIB$BIND_CALLBACKS(this);
      }
      
      public final void request() {
          MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
          if (var10000 == null) {
              CGLIB$BIND_CALLBACKS(this);
              var10000 = this.CGLIB$CALLBACK_0;
          }
  
          if (var10000 != null) {
              var10000.intercept(this, CGLIB$request$0$Method, CGLIB$emptyArgs, CGLIB$request$0$Proxy);
          } else {
              super.request();
          }
      }
  }
  ```

  CGLIB 生成的代理类比较复杂，先来说明4个成员变量：

  1. `CGLIB$CALLBACK_0`：在`enhance.setCallback()`中传入的`MethodIncterceptor`对象
  2. `CGLIB$request$0$Method`：Java 反射的`Method`类型对象，即被代理的`request()`方法的包装类
  3. `CGLIB$request$0$Proxy`：CGLIB 自行实现的 反射中的`request()`包装类，执行效率比`Method`更快
  4. `CGLIB$emptyArgs`：调用`request()`方法的参数，无参时为空数组

  `request()`方法内的逻辑就比较简单了，判断`MethodIncterceptor`对象是否为空，空就绑定一个。如果确实没有设置就不管了。非空的话调用方法并传入之前说明的三个成员变量作为方法参数。



##### CGLIB 动态代理调用

```kotlin
CGLIBProxy(RealSubject()).request()
```

结果如下

> called before request
> 
> **CGLIB Proxy is working**
> 
> **Real Subject is requesting**
> 
> called after request



#### JDK 代理和 CGLIB 代理的区别

+ JDK 代理只能针对类生成代理，但 CGLIB 代理能针对类生成代理
+ 在 JDK 1.8 后，CGLIB 代理执行效率比 JDK 代理更高



### 静态代理和动态代理的区别

+ 静态代理只能静态的完成代理，被代理类增加新方法需要同步在代理类添加代码，违反开闭原则。而动态代理的代理类动态生成，不需要手动扩张，符合开闭原则



## 代理模式的优缺点

### 优点

+ 将代理对象与被代理对象分离，被代理对象只专注于业务逻辑，其他边角逻辑由代理类处理。降低耦合性
+ 可以一定程度上增加和保护实际角色

### 缺点

+ 代理模式会使代码结构变得复杂
+ 代理模式会导致处理速度变慢