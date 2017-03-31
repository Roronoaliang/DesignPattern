## 定义

> 当一个对象内在状态改变时允许其改变行为，这个对象看起来像改变了其类。

状态模式的核心是封装状态，由状态的变更引起了行为的变更，从外部看来就像这个对象对应的类的行为也发生了改变。

## 通用类图

![](https://ws1.sinaimg.cn/large/bc18b842gy1fdgmd02cm0j20l70ejt9b)

* State：抽象状态角色，负责对象状态的定义，并且封装了上下文角色来实现状态切换。
* ConcreteState：具体状态角色，每一个具体状态必须实现对本状态行文的管理和趋向状态的处理，即处于本状态下的业务逻辑和何时切换到其他状态。
* Context：上下文角色，定义客户端需要的接口，并负责具体状态的切换。

## 代码示例

* 抽象状态

```
public abstract class State {
    //定义上下文角色，供子类切换状态时访问
    protected Context context;
    public void setContext(Context c) {
        this.context = c;
    }
    //handle1和handle2是所有状态子类都具备的行为
    public abstract void handle1();
    public abstract void handle2();
}
```

* 具体状态1

```
public class ConcreteState1 extends State {
    public void handle1() {
        //状态1下表现的行为1
    }
    public void handle2() {
        //更新状态为状态2
        super.context.setCurrentState(Context.STATE2);
        //切换到状态2后委托给context执行新状态下表现的行为
        super.context.handle2();
    }
}
```

* 具体状态2

```
public class ConcreteState2 extends State {
    public void handle1() {
        //更新状态为state1
        super.context.setCurrentState(Context.STATE1);
        //切换到状态1并委托context执行状态1下的行为
        super.context.handle1();
    }
    public void handle2() {
        //状态1下表现的行为2
    }
}
```

* 上下文角色

```
public class Context {
    //定义所有的状态
    public final static State STATE1 = new ConcreteState1();
    public final static State STATE2 = new ConcreteState2();
    //记录当前状态
    private State currentState;
    public State getCurrentState() {
        return currentState;
    }
    public void setCurrentState(State currentState) {
        //更新Context保存的State
        this.currentState = currentState;
        //更新State下的Context
        this.currentState.setContext(this);
    }

    //行为委托
    public void handle1() {
        this.currentState.handle1();
    }
    public void handle2() {
        this.currentState.handle2();
    }

}
```

* 场景类

```
public class Client {
    public static void main(String[] args) {
        Context context = new Context();
        //设置初始状态
        context.setCurrentState(new ConcreteState1());
        context.handle1();
        context.handle2();
    }
}
```

## 优缺点

优点：

* 结构清晰，避免使用过多的条件判断语句，使程序代码更简洁，提高可维护性。
* 符合单一职责原则和开闭原则。将状态的变化过程隐藏，使外部只看到行为发生改变而无需关心状态如何改变。

缺点：

* 状态过多时会导致状态子类过多，而且状态之间的切换逻辑越多程序会越复杂。

## 使用场景举例

* 行为随状态改变而改变的场景，如权限的设计，人员的状态不同执行相同的操作表现出不同的行为。
* 代替大量的条件、分支判断语句。

## 与策略模式的区别

* Context上下文角色的职责不同：策略模式的Contex起到委托的作用，负责算法的替换。而状态模式的Context角色除了执行委托还负责登记状态的变化。
* 关注点不同：策略模式关注的是算法的完整性、封装性和自由替换的问题。而状态模式旨在解决内部状态发生改变而带来的行为改变问题。
* 应用场景不同：策略模式封装的一组算法必须是平行的、可相互替换的。而状态模式要求的是有状态且有行为的场景。