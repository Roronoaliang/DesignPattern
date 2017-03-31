## 定义

> 观察者模式也称之为发布订阅模式，它定义了对象间一种一对多的依赖关系，使得每当一个对象改变状态，则所有依赖于它的对象都会得到通知并被自动更新。

## 通用类图

![](https://ws1.sinaimg.cn/large/bc18b842gy1fd7qd84j84j20qm0aht9a)

* Observable：被观测者需实现的接口，定义了被观测者需要实现的职责：能够动态的增加、删除观测者并通知观测者。
* Observer：观察者接口，定义了接收到信息后进行更新的操作。
* ConcreteSubject：具体的被观察者。
* ConcreteObserver：具体的观察者。

## 代码示例

* 被观察者抽象类

```
public abstract class Observable {
    private Vector<Observer> observers = new Vector<Observer>();
    public void addObserver(Observer o) {
        this.observers.add(o);
    }
    public void delObserver(Observer o) {
        this.observers.remove(o);
    }
    public void notifyObservers(){
        for(Observer o : this.observers) {
            o.update("状态更新");
        }
    }
}
```

* 具体的观察者

```
public class ConcreteSubject extends Observeable {
    //具体业务
    public void doSomething() {
        super.notifyObservers();
    }
}
```

* 观察者接口

```
public interface Observer {
    public void update(String content);
}
```

* 具体观察者

```
public class ConcreteObserver implements Observer {
    public void update(String content) {
        System.out.println(content);
    }
}
```

* 场景类

```
public class Client {
    public static void main(String[] args) {
        //定义被观察者
        ConcreteSubject subject = new ConcreteSubject();
        //定义观察者
        Observer obs = new ConcreteObserver();
        //设置观察关系
        subject.addObserver(obs);
        subject.doSomething();
    }
}
```

## 优缺点

优点：

* 观察者和被观察者之间是抽象耦合，增加观察者或者被观察者都符合开闭原则。
* 使用观察者模式可以建立一套触发机制，将各个符合单一职责原则的类串联成复杂的逻辑关系。

缺点：

* 观察者模式会导致开发效率和运行效率问题，一个被观测者，多个观测者，在开发和调试时会比较复杂。而在运行时，一个观察者更新逻辑运行过久，会影响整体的执行效率，在这种情况下一般采用异步更新观察者的方式。

## 广播链（触发链）问题

数据库的触发器存在一个触发器链的问题，比如在A表上写了一个触发器，内容是一个字段被更新后更新B表的一条数据，而在B表上也有一个触发器，要更新C表，依此类推，最终数据库就崩了。
观察者模式同样存在这个问题，一个观察者，同时也是被观察者，一旦存在多个这样的情况形成一条链，整个逻辑会变得更加复杂，可维护性也非常差，因此使用观察者模式尽量避免消息的转发形成广播链。

使用观测者模式也能构成类似于责任链模式的链结构，不同的是：

* 链中的消息对象不同：责任链模式中不会对链中传递的消息对象进行结构上的改变。而在触发链中允许传递对象的自由变化，只要求链中相邻两个节点的消息对象固定。
* 消息的分销渠道不同：责任链模式中消息的传递是单方向的、固定的。而触发链则可以依不同的业务逻辑对消息进行不同的转发。