## 定义

> 定义一个用于创建对象的接口，让子类决定实例化哪一个类。工厂方法使一个类的实例化延迟到其子类。

## 通用类图

![](https://ws1.sinaimg.cn/large/bc18b842gy1fcuj89vek1j20ie09y0sx)

工厂方法模式中，抽象产品类AbstractProduct负责定义产品的共性，实现对事物最抽象的定义。抽象工厂类AbstractFactory定义了生产产品的接口，具体如何创建产品则由具体的工厂实现类ConcreteFactory实现。

## 代码示例

* 抽象产品类
```
public abstract class AbstractProduct {
    //产品类公共方法
    public void method1() {
    }
    //抽象方法
    public abstract void method2();
}
```

* 具体产品类
```
public class ConcreteProduct extends AbstractProduct {
    public void method2() {
    }
}
```

* 抽象工厂类
```
public abstract class Factory {
    public abstract <T extends AbstractProduct> T createProduct(Class<T> c);
}
```

* 具体工厂类
```
public class ConcreteFactory extends AbstractFactory {
    public <T extends AbstractProduct> T createProduct(Class<T> c) {
        AbstractProduct product = null;
        try {
            product = (AbstractProduct)Class.forName(c.getName()).newInstance();
        } catch (Exception e) {
            //异常处理
        }
        return (T)product;
    }
}
```

* 场景类

```
public class Client {
    public static void main(String[] args) {
    AbstractFactory factory = new ConcreteFactory();
    AbstractProduct product = factory.createProduct(ConcreteProduct.class);
    }
}
```

## 优点

* 工厂方法模式具有良好的封装性，代码结构清晰。
* 使用工厂方法模式创建对象不需要知道创建对象的过程，降低模块间的耦合。
* 工厂方法模式具有良好的扩展性，当需要增加产品类时，只要修改具体的工厂类或者扩展新的工厂类。
* 使用工厂方法模式调用者不需要关心产品类的内部实现和变化。

## 工厂方法模式的变化

* 缩小为简单工厂模式：去除抽象工厂的定义，并把具体工厂的工厂方法改写为静态方法，因此也称之为静态工厂模式。
* 扩展出多个工厂类：当为每一个产品类初始化的过程都很繁杂时，可以扩展出多个工厂类，每一个工厂类单独为一类产品服务，这样也更加符合单一职责原则。另外还可以再增加一个协调类，用来封装子工厂类的调用，使调用者不需要同多个工厂类直接交流。