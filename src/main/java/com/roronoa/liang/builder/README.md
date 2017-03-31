##　定义

> 建造者模式也叫生成器模式，是将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。

##　通用类图

![](https://ws1.sinaimg.cn/large/bc18b842gy1fcydyymsz4j20ni0d1q3h)

* Director：指导者，调用具体建造者来创建复杂对象的各个部分，在指导者中不涉及具体产品的信息，只负责保证对象各部分完整创建或按某种顺序创建。
* Builder：建造者接口，用来规范产品对象的各个组成成分的建造。建造者接口规定了要实现复杂对象的哪些部分的创建，并不涉及具体的对象部件的创建。
* ConcreteBuilder：针对不同的业务逻辑，具体化复杂对象的各部分的创建，在建造完成后能提供产品的示例。
* Product：产品接口抽象了要创建的复杂对象的共性。

## 代码示例

* 产品类

```
public class Product {
    public void doSomething() {
        //独立业务处理
    }
}
```

* 抽象建造者

```
public abstract class Builder {
    //设置产品的不同部分或顺序，从而获得不同的产品
    public abstract void setPart(List list);
    //建造产品
    public abstract Product buildProduct();
}
```

* 具体建造者

```
public class ConcreteBuilder extends Builder {
    private Product product = new Product();
    //设置产品零件组成
    public void setPart(List list) {
        //产品组成部分或组成顺序
    }
    //组建一个产品
    public Product bulidProduct() {
        return product;
    }
}
```

* 导演类

```
public class Director {
    //定义建造者对象
    private Builder builder = new ConcreteBuilder();

    //构建不同的产品
    public Product getProduct_A() {
        List list = new ArrayList();
        //设置不同的部件或顺序
        //......
        builder.setPart(list);
        return builder.buildProduct();
    }
}
```

导演类起到了封装的作用，避免高层模块深入到建造者内部的实现类，具体的产品组成部分或顺序则是由建造者来完成拼接。

## 优缺点

优点：

* 使用建造者模式可以使客户端不必知道产品内部组成的细节。
* 具体的建造者类之间是相互独立的，对系统的扩展非常有利。
* 由于具体的建造者使对立的，所以可以对建造过程逐步细化，而不对其他的模块产生影响。

## 使用场景举例

* 相同的方法，不同的执行顺序，会产生不同的结果时可以采用建造者模式。
* 多个部件都可以装配到一个对象中，但是产生的运行结果又不相同时可以采用。
* 产品类非常复杂，或者产品类中的调用顺序不同产生了不同的结果。

## 与工厂模式、抽象工厂模式的区别

建造者模式和工厂模式、抽象工厂模式都是创建型设计模式。从代码上看，建造者模式是将简单的产品创建顺序或部件组合到类中，从而创造出复杂的产品，关注的是按需“组装”多个部分，从而获得一个集合。而工厂模式、抽象工厂模式关注的是创建单个产品。也就是说抽象工厂模式、工厂模式对产品的控制粒度要粗，它们关注的都是产品整体，而建造者模式关注的是构建过程。如果希望屏蔽对象的创建过程，只提供一个封装良好的对象，则可以选择工厂方法或抽象工厂模式，而如果希望能通过装配不同的组件或不同顺序来得到全新的对象，则可以采用建造者模式。