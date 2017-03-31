## 定义

> 用一个中介对象封装一系列的对象交互，中介者使各对象不需要显式地相互作用，从而使其耦合松散，而且可以独立地改变它们之间的交互。

## 通用类图

![](https://ws1.sinaimg.cn/large/bc18b842gy1fd0i797ku5j20g90a1glq)

* Mediator：抽象中介者角色，用于各个同事角色之间的通信。
* ConcreteMediator：具体的中介者角色，通过协调各同事角色来实现协作行为，所以中介者必须依赖各个同事角色。
* Colleague：同事角色，每一个同事角色与其他同事角色通信时，都要通过中介者协作。同事角色类的行为有两种。一种是本身的行为，与其他同事类没有任何依赖，称之为自发行为。另一种是必须依赖中介者才能完成的行为，称之为依赖方法。


## 代码示例

* 抽象中介者

```
public abstract class Mediator {
    //同事类,不同同事类可能有不同的业务方法，所以这里使用实际类型
    protected ConcreteColleague1 c1;
    protected ConcreteColleague2 c2;
    //同事类有多个，通过getter/setter注入同事类
    public ConcreteColleague1 getC1() {
        return c1;
    }
    public void setC1(ConcreteColleague1 c1) {
        this.c1 = c1;
    }
    public ConcreteColleague2 getC2() {
        return c2;
    }
     public void setC2(ConcreteColleague2 c2) {
        this.c2 = c2;
    }
    //中介者模式的业务逻辑
    public abstract void doSomething1();
    public abstract void doSomething2();
}
```

* 中介者实现

```
public class ConcreteMediator extends Mediator {
    public void doSomething1() {
        //协同各同事类的行为
        super.c1.selfMethod1();
        super.c2.selfMethod2();
    }
    public void doSomething2() {
    //协同各同事类的行为
        super.c1.selfMethod1();
        super.c2.selfMethod2();
    }
}
```

* 抽象同事类

```
public abstract class Colleague {
    //每个同事类都要依赖于中介者来提供协同服务
    protected Mediator mediator;
    public Colleague(Mediator mediator) {
        this.mediator = mediator;
    }
}
```

* 具体同事类

```
public class ConcreteColleague1 extends Colleague {
    public ConcreteColleague1(Mediator mediator) {
        super(mediator);
    }
    //自有方法
    public void selfMethod1() {
        //只依赖自身的业务逻辑
    }
    //依赖方法，委托给中介者处理
    public void depMethod1() {
        //处理自身的业务逻辑
        this.selfMethod1();
        //自己不能处理的业务逻辑交给中介者
        super.mediator.doSomething1();
    }
}


public class ConcreteColleague2 extends Colleague {
    public ConcreteColleague2(Mediator mediator) {
        super(mediator);
    }
    //自有方法
    public void selfMethod2() {
        //只依赖自身的业务逻辑
    }
    //依赖方法，委托给中介者处理
    public void depMethod2() {
        //处理自身的业务逻辑
        this.selfMethod2();
        //自己不能处理的业务逻辑交给中介者
        super.mediator.doSomething2();
    }
}
```

## 优缺点

优点：

* 减少类间的依赖，可以把原有的一对多的依赖变成一对一的依赖，同事类只依赖于中介者，减少了依赖就是降低了类间的耦合。

缺点：

* 过多的使用中介者模式会导致中介者膨胀得很大，逻辑复杂。

## 应用场景举例

* 当多个对象之间产生相互的依赖关系，导致依赖关系类图形成网状结构时，可以考虑采用中介者模式装换为星型结构。
* 多个对象有依赖关系，但是依赖的行为尚不确定或者有发生改变的可能时，采用中介者模式可以降低变更带来的风险扩散。
* 产品开发过程可以考虑中介者模式提高产品的性能和扩展性，如MVC框架，但是项目开发尽量少用中介者模式。

## 与门面模式的区别

* 功能不同：门面模式仅仅增加了一个屏蔽子系统直接访问的门面，对原本的系统而言没有增加任何的功能，子系统完全独立于门面。而中介者模式将各个同事类原有的耦合关系移植到了中介者对象中，同事类不能脱离中介者独立存在。
* 耦合程度不同：对门面模式而言，子系统不知道门面对象的存在，而中介者模式中，每个同事类都知道中介者对象的存在。
* 封装程度不同：门面模式是一种简单的封装，所有的请求处理都委托给子系统完成。而中介者模式需要由中介者来协调同事类完成业务，本身也可能完成部分业务，属于更进一步的功能封装。