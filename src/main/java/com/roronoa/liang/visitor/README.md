## 定义

> 封装一些作用于某种数据结构中的各元素的操作，它可以在不改变数据结构的前提下定义作用于这些元素的新操作。

## 通用类图

![](https://ws1.sinaimg.cn/large/bc18b842gy1fddf51xhl5j20kp0hadgk)

* Visitor：抽象访问者，声明访问者可以访问哪些元素。
* ConcreteVisitor：具体访问者。
* Element：抽象元素，声明接受哪一类访问者访问。
* ConcreteElement：具体元素，实现accept方法，通常是vistor.visit(this)。
* ObjectStuture：结构对象，一般是集合类，用来容纳多个元素。

## 代码示例

* 抽象元素

```
public abstract class Element {
    public abstract void doSomething();
    public abstract void accept(IVisitor v);
}
```

* 具体元素

```
public class ConcreteElement1 extends Element {
    public void doSomething() {
        System.out.println("具体元素1");
    }
    public void accept(IVisitor v) {
        visitor.visit(this);
    }
}
```

<br/>

```
public class ConcreteElement2 extends Element {
    public void doSomething() {
        System.out.println("具体元素2");
    }
    public void accept(IVisitor v) {
        visitor.visit(this);
    }
}
```

* 抽象访问者

```
public interface IVisitor {
    //定义可以访问哪些元素
    void visit(ConcreteElement1 e1);
    void visit(ConcreteElement2 e2);
}
```

* 具体访问者

```
public class Visitor implements IVisitor {
    public void visit(ConcreteElement1 e1) {
        System.out.println("访问者独特的访问方式");
        e1.doSomething();
    }
    public void visit(ConcreteElement2 e2) {
        e2.doSomething();
    }
}
```

* 场景类

```
public class Client {
    public static void main(String[] args) {
        List<Element> list = new ArrayList<Element1>();
        for(int i = 0; i < 5; i++) {
            list.add(new ConcreteElement1());
            list.add(new ConcreteElement2());
        }
        for(int i = 0; i < 10; i++) {
            list.get(i).accept(new Visitor());
        }
    }
}
```

## 优缺点

优点：

* 符合单一职责原则，元素被访问的表现行为即展现由访问者Visitor来执行。
* 较好的扩展性，当需要新增新的数据展现形式时只需要增加新的访问者或者访问方法即可。
* 灵活性高，针对不同的具体元素对象，能单独对各自的行为在访问者中进行增强。

缺点：

* 访问者需要了解具体元素的内部细节，这不符合迪米特原则。
* 具体元素与相应的访问者出现了耦合，导致具体元素变更困难。
* 访问者依赖的是具体元素，违背了依赖倒置原则。

## 使用场景举例

* 业务规则需要对同属一个基类或抽象的多个不同对象进行遍历或不相同的操作，而且又不想让这些操作污染到这些对象本身。
* 拦截器。