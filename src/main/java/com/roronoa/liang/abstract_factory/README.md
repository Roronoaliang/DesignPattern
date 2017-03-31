## 定义

> 为创建一组相关或相互依赖的对象提供一个接口，而无需指定它们的具体类。

## 通用类图

![](https://ws1.sinaimg.cn/large/bc18b842gy1fcwwhbdww0j20vf0fq75n)

抽象工厂是工厂方法模式的升级，当有多个业务品种或分类时，工厂方法就可以升级为抽象工厂解决问题。
在抽象工厂方法中，两个互相影响的产品线也叫产品族。

## 代码示例

* 抽象产品接口

```
public interface ProductInterface {
    //抽象方法，不同产品类各自实现
    void method1();
}
```

* A产品家族抽象类
```
public abstract class AbstractProductA implements ProductInterface {
    //共有方法
    public void shareMethod() {
    }
}
```

* 产品A1实现类

```
public class ProductA1 extends AbstractProductA {
    public void method1() {
        System.out.println("产品A1实现");
    }
}
```

* 产品A2实现类

```
public class ProductA2 extends AbstractProductA {
    public void method1() {
        System.out.println("产品A2实现");
    }
}
```

* B产品家族抽象类

```
public abstract class AbstractProductB implements ProductInterface {
    //共有方法
    public void shareMethod() {
    }
}
```

* 产品B1实现类

```
public class ProductB1 extends AbstractProductB {
    public void method1() {
        System.out.println("产品B1实现");
    }
}
```

* 产品B2实现类

```
public class ProductB2 extends AbstractProductB {
    public void method1() {
        System.out.println("产品B2实现");
    }
}
```

* 抽象工厂类

```
public interface AbstractFactory {
    //创建A类产品家族
    AbstractProductA createProductA();
    //创建B类产品家族
    AbstractProductB createProductB();
}
```

有多少个产品族，在抽象工厂类中就有多少个创建方法。

* 工厂实现类1

```
public class Factory1 implements AbstractFactory {
    //只生产A1类产品
    public AbstractProductA createProductA() {
        return new ProductA1();
    }

    //只生产B1类产品
    public AbstractProductB createProductB() {
        return new ProductB1();
    }
}
```

* 工厂实现类2

```
public class Factory2 implements AbstractFactory {
    //只生产A2类产品
    public AbstractProductA createProductA() {
        return new ProductA2();
    }

    //只生产B2类产品
    public AbstractProductB createProductB() {
        return new ProductB2();
    }
}
```

有多少个产品等级就有多少个实现工厂类。

* 场景类

```
public class Client {
    //创建工厂1对象
    AbstractFactory factory1 = new Factory1();
    //生产A1对象
    AbstractProductA a1 = factory1.createProductA();
    //生产B1对象
    AbstractProductB b1 = factory1.createProductB();
    //创建工厂2对象
    AbstractFactory factory2 = new Factory2();
    //生产A2对象
    AbstractProductA a2 = factory2.createProductA();
    //生产B2对象
    AbstractProductB b2 = factory2.createProductB();
}
```

## 优缺点

优点：

* 抽象工厂模式具有良好的封装性。高层模块不需要关心每个产品的实现类和对象的创建细节。
* 产品族内的约束为非公开状态。调用者不知道产品之间存在哪些约束，由工厂实现类实现各个产品族之间的约束。

缺点：

* 抽象工厂模式最大的缺点是产品族扩展困难。例如增加一个产品类，抽象工厂需要增加一个方法，工厂实现类也要修改，这违背了开闭原则。

## 使用场景举例

当一个对象族有相同的约束，就可以使用抽象工厂模式，例如同一个功能在Linux和Windows下有不同的实现代码，那就有了相同的约束条件，就可以使用抽象工厂模式。

## 注意事项

在抽象工厂模式中，产品族的扩展比较困难，而不是产品等级扩展困难。增加一个产品等级，只需要增加一个工厂类负责新增的产品即可。也就是说横向扩展容易，纵向扩展困难。