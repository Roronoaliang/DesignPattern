## 定义

> 门面模式也称之为外观模式，它要求一个子系统的外部与其内部的通信必须通过一个统一的对象进行。门面模式提供一个高层次的接口，使得子系统更易于使用。

门面模式注重的是提供一个访问子系统的接口，除了这个接口外不允许有任何访问子系统的行为发生。另外，门面角色要不参与子系统内的业务逻辑，保证发生变化时只在子系统内部变化，对外提供访问入口的门面角色无需变化。

## 通用类图

![](https://ws1.sinaimg.cn/large/bc18b842gy1fd8bviws40j20930a8weh)

* Facade：门面角色，客户端访问子系统的所有功能都通过调用该角色的方法来完成，从职责上看，门面角色没有实际的业务逻辑，只是一个委托类。
* SubSystem：子系统角色，是一系列类的集合，子系统并不知道门面角色的存在。

## 代码示例

* 子系统

```
public class ClassA {
    public void methodA() {
        System.out.println("A");
    }
}
```
<br/>
```
public class ClassB {
    public void methodB() {
        System.out.println("B");
    }
}
```
<br/>
```
public class ClassC {
    public void methodC() {
        System.out.println("C");
    }
}
```

* 门面角色

```
public class Facade {
    //被委托的对象
    private ClassA a = new ClassA();
    private ClassB b = new ClassB();
    private ClassC c = new ClassC();
    public void methodA() {
        this.a.methodA();
    }
    public void methodB() {
        this.b.methodB();
    }
    public void methodC() {
        this.c.methodC();
    }
}
```

* 场景类

```
public class Client {
    public static void main(String[] args) {
        Facade facade = new Facade();
        //调用子系统方法统一通过门面角色来完成
        facade.methodA();
        facade.methodB();
        facade.methodC();
    }
}
```

## 优缺点

优点：

* 减少系统的相互依赖，所有的依赖都是对门面对象的依赖，与子系统无关。
* 依赖减少的同时带来了灵活性的提高。
* 提高了访问子系统的安全性。

缺点：

* 违背了开闭原则，一旦发生变化需要修改门面角色的代码。

## 使用场景举例

* 为一个复杂的模块或子系统提供一个供外界访问的接口。
* 子系统相对独立，外界访问子系统只要黑箱操作即可。