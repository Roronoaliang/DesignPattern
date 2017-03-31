## 定义

> 桥梁模式也叫桥接模式，它将抽象和实现解耦，使得两者可以独立地变化。

## 通用类图

![](https://ws1.sinaimg.cn/large/bc18b842gy1fdjastftawj20jd0bb3yu)

* Abstraction：抽象化角色，其主要职责是定义出该角色的行为，同时保存一个对实现化角色的引用。
* Implementor：实现化角色。定义角色必须具备的行为和属性。
* RefinedAbstraction：修正抽象化角色，引用实现化对象对抽象化角色进行修正。
* ConcreteImplementor：具体实现化角色。

## 代码示例

* 实现化角色

```
public interface Implementor {
    public void doSomething();
    public void doAnything();
}
```

* 具体实现化角色

```
public class ConcreteImplementor_1 implements Implementor {
    public void doSomething() {
        System.out.println("do something 1");
    }
    public void doAnything() {
        System.out.println("do anything 1");
    }
}
```

<br/>

```
public class ConcreteImplementor_2 implements Implementor {
    public void doSomething() {
        System.out.println("do something 2");
    }
    public void doAnything() {
        System.out.println("do anything 2");
    }
}
```

* 抽象化角色

```
public abstract class Abstraction {
    //对实现化角色的引用
    private Implementor imp;
    //约束子类必须有该构造函数
    public Abstraction(Implementor imp) {
        this.imp = imp;
    }
    public Implementor getImp() {
        return imp;
    }
    //自身行为
    public void request() {
        this.imp.doSomething();
    }
}
```

* 具体抽象化角色

```
public class RefinedAbstraction extends Abstraction {
    public RefinedAbstraction(Implementor imp) {
        super(imp);
    }
    //修正父类行为
    @Override
    public void request() {
        //do something before
        super.request();
        //do something after
    }
}
```

* 场景类

```
public class Client {
    public static void main(String[] args) {
        Implementor imp = new ConcreteImplementor();
        Abstraction abs = new RefinedAbstraction(imp);
        abs.request();
    }
}
```

## 优缺点

优点：

* 抽象与实现分离，实现可以不受抽象的约束。规避了继承的缺点，如三个父子关系的类：Father->Son->GrandSon，如果使用继承，Son类要重写Father类的方法，而GrandSon不重写Father类的方法，使用继承无法很好的解决这类问题，而使用桥梁模式则没有这种限制。
* 对于实现化角色和抽象化角色都有很好的扩展能力。