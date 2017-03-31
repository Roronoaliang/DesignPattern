## 定义

> 定义一组算法，将每个算法都封装起来，并且使它们之间可以互换。

## 通用类图

![](http://ww3.sinaimg.cn/large/0065HC85gw1f3dhb6mak0j30h308umyj.jpg)

* Context:上下文角色类，起封装作用，屏蔽高层模块对策略的直接访问，封装可能存在的变化。
* Strategy:抽象策略接口或抽象类，是策略族的抽象，定义每个策略或者算法必须具有的方法和属性。
* ConcreteStrategy:具体的策略实现类。

## 代码示例

* 抽象策略接口

```
public interface Strategy {
    public void algorithm();
}
```

* 具体策略1

```
public class ConcreteStrategy1 implements Strategy {
    public void algorithm() {
        System.out.println("使用冒泡排序完成要求");
    }
}
```

* 具体策略2

```
public class ConcreteStrategy2 implements Strategy {
    public void algorithm() {
        System.out.println("使用快速排序完成要求");
    }
}
```

* 上下文封装角色

```
public class Context {
    private Strategy strategy = null;
    public Context(Strategy strategy) {
        this.strategy = strategy;
    }
    //封装后的策略方法
    public void doSomething() {
        this.strategy.algorithm();
    }
}
```

* 场景类

```
public class Client {
    public static void main(String[] args) {
        //声明具体的策略
        Strategy strategy = new ConcreteStrategy1();
        //声明上下文对象
        Context context = new Context(strategy);
        context.doSomething();
    }
}
```

## 优缺点

优点：

* 可以对外提供可自由切换的策略。
* 扩展性良好，符合开闭原则，增加子策略只要实现策略接口就可以了。
* 对外提供的访问接口是封装类，避免了使用多重条件判断

缺点：

* 类的数量增多，因此策略过多的情况考虑使用混合模式，解决策略类膨胀和对外暴露的问题。
* 所有的策略类都必须对外暴露，这样上层模块才能知道具体调用那个策略，这违反了迪米特原则（可以使用工厂方法、代理或者享元模式去修正这个缺点）。