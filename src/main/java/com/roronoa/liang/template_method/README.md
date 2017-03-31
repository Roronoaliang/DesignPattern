## 定义

> 定义一个操作中的算法的框架，而将一些步骤延迟到子类中。使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤。

## 通用类图

![](https://ws1.sinaimg.cn/large/bc18b842gy1fcy6nk8eznj20fq08ymxc)

模板方法中AbstractClass是抽象模板，抽象模板包含两类方法：

* 基本方法，是由子类来实现的方法，并且在模板方法中被调用。
* 模板方法，是一个操作中算法的框架，实现对基本方法的调度来完成固定的逻辑。抽象模板中可以由多个不同的模板方法，并且为了防止恶意的操作，模板方法一般都加上final防止覆写。

## 代码示例

* 抽象模板类

```
public abstract class AbstractClass {
    protected abstract void firstStep();
    protected abstract void secondStep();
    public void templateMethod() {
        this.firstStep();
        this.secondStep();
    }
}
```

* 模板实现类

```
public class ConcreteClass1 extends AbstractClass {
    protected void firstStep() {
        System.out.println("步骤1");
    }
    protected void secondStep() {
        System.out.println("步骤2");
    }
}
```

```
public class ConcreteClass2 extends AbstractClass {
    protected void firstStep() {
        System.out.println("不同的处理方式1");
    }
    protected void secondStep() {
        System.out.println("不同的处理方式2");
    }
}
```

* 场景类

```
public class Client {
    public static void main(String[] args) {
        AbstractClass class1 = new ConcreteClass1();
        AbstractClass class2 = new ConcreteClass2();
        class1.templeteMethod();
        class2.templeteMethod();
    }
}
```

## 优缺点

优点：

* 封装不变部分，扩展可变部分。
* 提取公共代码，便于维护。
* 行为由父类控制，细节由子类实现，符合开闭原则。

缺点：

* 模板方法模式与我们一般的设计习惯相颠倒，在代码阅读上可能会产生一定的不适应。