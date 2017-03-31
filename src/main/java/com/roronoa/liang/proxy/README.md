## 定义

> 为其他对象提供一种代理以控制对这个对象的访问。

## 通用类图

![](https://ws1.sinaimg.cn/large/bc18b842gy1fcz8myar9dj20fn09iq36)

* Subject接口是抽象主题角色，抽象出基本的业务类型定义，再代理模式中没有特殊的要求。
* RealSubject是具体的主题角色，也是被代理的对象。
* Proxy是代理类，和被代理类实现同一个接口或继承同一个抽象类。客户端通过对Proxy的操作达到操作RealSubject的效果。

## 代码示例

* 抽象主题类

```
public interface Subject {
    //定义一个普通的业务方法
    void request();
}
```

* 被代理类

```
public class RealSubject implements Subject {
    public void request() {
        System.out.println("执行具体的业务逻辑");
    }
}
```

* 代理类

```
public class Proxy implements Subject {
    //持有被代理类的引用
    private Subject subject = null;
    //传递实际被代理对象的引用
    public Proxy(Subject subject) {
        this.subject = subject;
    }
    //被代理方法，可以在被代理方法前后扩展额外的操作
    public void request() {
        this.before(); //预处理
        this.subject.request(); //被代理类的业务逻辑
        this.after(); //后处理
    }
    private void before() {
        //do something
    }
    private void after() {
        //do something
    }
}
```

## 优缺点

优点：

* 代理模式可以提供非常好的访问控制。
* 代理模式有较好的扩展性，当新增具体的主题角色时代理类可以不需要做任何修改。
* 代理模式可以在目标对象方法的基础上作增强，通常是对目标对象的方法进行拦截和过滤。

缺点：

* 由于在客户端和真实主题之间增加了代理对象，所以代理模式可能会造成请求的处理速度变慢。

## 代理模式的扩展

### 1. 普通代理

普通代理的要求是约束客户端只能访问代理角色，而不能直接访问真实角色。这种可以通过技术约束的方式，但是对系统的维护反而不利，因此普通代理模式的约束问题，尽量通过团队约定来禁止new一个真实的主题对象，使每一个主题类都是可被重用和可维护的。

### 2. 强制代理

强制代理与一般的代理截然相反，它强制的是客户端必须通过真实角色查找到代理角色才能访问，不允许直接访问真实角色也不允许使用客户端产生的代理来访问。简单的说，就是客户端new了一个真实主题的对象，返回的却是代理对象，代理对象的管理由真实主题对象内部自己完成。

## 动态代理

动态代理是在实现阶段不用关心代理了谁，而在运行阶段才指定被代理对象。而需要我们自己写代理类的方式则属于静态代理。

### 动态代理通用类图

![](https://ws1.sinaimg.cn/large/bc18b842gy1fczlffe4vgj20qf0cymxw)

### 代码示例

* 抽象主题

```
public interface Subject {
    void doSomething(String arg);
}
```

* 真实主题

```
public class RealSubject implements Subject {
    public void doSomething(String arg) {
        System.out.println("业务处理");
    }
}
```

* 处理器MyInvocationHandler

```
public class MyInvocationHandler implements InvocationHandler {
    //被代理对象的引用
    private Object target = null;
    //通过构造函数传递被代理对象
    public MyInvocationHandler(Object object) {
        this.target = object;
    }
    //被代理方法,通过反射执行真实主题中的方法
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(this.target, args);
    }
}
```

* 动态代理类

```
public class DynamicProxy<T> {
    public static <T> T newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h) {
        //寻找JoinPoint连接点，AOP框架中通过配置来定义
        if(true) {
        //执行一个前置通知
        (new BeforeAdvice()).exec();
    }
    //执行真实主题的目标方法
    return (T)Proxy.newProxyInstance(loader, interfaces, h);
}
```

* 通知接口

```
public interface IAdvice {
    //通知表现的是行为，只需要一个执行通知的方法即可
    void exec();
}
```

* 前置通知实现

```
public class BeforeAdvice implements IAdvice {
    public void exec() {
        System.out.println("前置处理");
    }
}
```

* 场景类

```
public class Client {
    public static void main(String[] args) {
        //动态代理一般需要通过真实主题来创建代理对象
        Subject subject = new RealSubject();
        //定义处理器
        InvocationHandler handler = new MyInvocationHandler(subject);
        //创建代理对象
        Subject proxy = DynamicProxy.newProxyInstance(subject.getClass().getClassLoader(), subject.getClass().getInterfaces(), handler);
        //通过代理对象执行目标方法
        proxy.doSomething("param");
    }
}
```