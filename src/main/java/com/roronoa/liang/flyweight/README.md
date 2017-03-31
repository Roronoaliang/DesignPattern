## 定义

> 使用共享对象可有效地支持大量的细粒度的对象。

享元模式是实现池技术的一种重要实现方式。享元模式中细粒度的对象要求是将对象信息分成内部状态和外部状态两个部分。内部状态是对象可共享出来的信息，存储在享元对象内部并且不会随着环境改变而改变。外部状态则是对象得以依赖的一个标记，是随着环境改变而改变的。
享元模式的目的在于运用共享技术，使得一些细粒度的对象可以共享，也便于重用或重构。

## 通用类图

![](https://ws1.sinaimg.cn/large/bc18b842gy1fdj7f4qhmdj20n00ee3z4)

* Flyweight：抽象享元角色，同时定义出产品对象的外部状态和内部状态的接口。
* ConcreteFlyweight：具体享元角色，是具体的产品类，实现抽象角色定义的业务。
* UnsharedConcreteFlyweight：不可共享的享元角色。不存在外部状态或者安全要求（如线程安全）不能够使用共享技术的对象，该对象一般不会出现在享元工厂中。
* FlyweightFactory：享元工厂，作用是构造一个池容器，同时提供从池中获得对象的方法。

## 代码示例

* 抽象享元角色

```
pulblic abstract class Flyweight {
    //内部状态
    private String internalState;
    //外部状态,对于确认只需要一次赋值的属性设置为final类型确保安全
    protected final String externalState;
    //要求享元对象必须接受外部状态
    public Flyweight(String externalState) {
        this.externalState = externalState;
    }
    //业务操作
    public abstract void operate();
    //内部状态getter/setter
    public String getInternalState() {
        return internalState;
    }
    public void setInternalState(String state) {
        this.internalState = state;
    }
}
```

* 具体享元角色

```
public class ConcreteFlyweight extends Flyweight {
    public ConcreteFlyweight(String externalState) {
        super(externalState);
    }
    public void operate() {
        //do something
    }
}
```

* 享元工厂

```
public class FlyweightFactory {
    //充当池容器
    private static HashMap<String, Flyeight> pool = new HashMap<>();
    public static Flywieght getFlyweight(String externalState) {
        Flyweight flyweight = null;
        if(pool.containsKey(externalState)) {
            flyweight = pool.get(externalState);
        } else {
            flyweight = new ConcreteFlyweight(externalState);
            pool.put(externalState, flyweight);
        }
        return flyweight;
    }
}
```

## 优缺点

优点：

* 享元模式通过划分出细粒度的对象达到对象共享的目的，能够减少程序创建的对象，降低内存使用。

缺点：

* 需要分离出外部状态和内部状态，提高了系统的复杂性。另外使用享元模式需要特别考虑线程安全的问题。