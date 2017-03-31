## 定义

> 将一个请求封装成一个对象，并传给调用对象。调用对象寻找可以处理该命令的合适对象，并把该命令传给相应的对象，该对象执行命令。

## 通用类图

![](https://ws1.sinaimg.cn/large/bc18b842gy1fd0sdzolq8j20uf09swez)

* Invoker：调用者对象，负责接收客户端传达的命令。
* Command：命令的抽象，封装需要执行的命令。
* ConcreteCommand：具体的命令对象，负责调用Receiver对象执行客户端的需求。
* Receiver：抽象接收者，定义了每个接收者完成的业务方法，是命令的最终执行者。

## 代码示例

* 抽象接收者

```
public abstract class Receiver {
    //定义每个接收者必须完成的业务
    public abstract void businessMethod();
}
```

* 具体的Receiver类

```
public class ConcreteReciver1 extends Receiver {
    public void businessMethod() {
        System.out.println("执行删除对象1的业务逻辑");
    }
}
```
<br/>
```
public class ConcreteReciver2 extends Receiver {
    public void businessMethod() {
        System.out.println("执行删除对象2的业务逻辑");
    }
}
```

* 命令接口

```
public interface Command {
    //每个命令类都必须有一个执行命令的方法
    void execute();
}
```

* 具体的命令类

```
public class ConcreteCommand1 extends Command {
    //接收者为ConcreteReciver1
    private Receiver receiver = new ConcreteReciver1();
    public void execute() {
        //业务处理
        this.receiver.businessMethod();
    }
}
```
<br/>
```
public class ConcreteCommand2 extends Command {
    //接收者为ConcreteReciver2
    private Receiver receiver = new ConcreteReciver2();
    public void execute() {
        //业务处理
        this.receiver.businessMethod();
    }
}
```

* 调用者对象

```
public class Invoker {
    //每个接收者目的都是接受客户端传达的命令
    private Command command;
    public void setCommand(Command command) {
        this.command = command;
    }
    public void action() {
        //执行命令
        this.command.execute();
    }
}
```

* 场景类

```
public class Client {
    public static void main(String[] args) {
        //声明接收者
        Invoker invoker = new Invoker();
        //声明命令1
        Command command1 = new ConcreteCommand1();
        //声明命令2
        Command command2 = new ConcreteCommand2();
        //执行命令1
        invoker.setCommand(command1);
        invoker.action();
        //执行命令2
        invoker.setCommand(command2);
        invoker.action();
    }
}
```

## 优缺点

优点：

* 类间解耦，调用者对象和接收者角色之间没有依赖关系，客户端执行命令时不需要了解到底是哪个接收者来执行。
* 可扩展性较好，Command可以非常容易的增加实现类，而调用者Invoker类不需要发生改变。

缺点：

* 当命令较多时Command子类会膨胀得非常大。

## 与策略模式的区别

* 关注点不同：策略模式关注的是算法的完整性、封装性和自由更替。而命令模式关注的是请求者和执行者的解耦，将请求的内容封装成一个一个的命令，由接收者执行。
* 角色功能不同：策略模式中的具体算法是负责一个完整算法逻辑，是不可再拆分的原子业务。而命令模式关注命令的实现，对接收者的变更不会对请求者有任何影响。
* 使用场景不同：策略模式适用于算法要求变换的场景。而命令模式适用于解耦两个有紧耦合关系的对象场合或者多命令多撤销的场景。