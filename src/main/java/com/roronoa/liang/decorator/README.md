## 定义

> 动态地给一个对象添加一些额外的职责。就增加功能而言，装饰模式比生成子类更加灵活。

## 通用类图

![](https://ws1.sinaimg.cn/large/bc18b842gy1fd1jr5ddsbj20hj0domxm)

* Component：抽象构件，是业务处理的核心对象，也是装饰模式最基本、最原始的抽象层。
* ConcreteComponent：具体构件，是被装饰的对象。
* Decorator：装饰角色，一般是抽象类，持有Component变量用来保存被装饰对象。
* ConcreteDecorator：具体的装饰角色。

## 代码示例

* 抽象构件

```
public interface Component {
    //可以被装饰的业务方法
    void opeate();
}
```

* 具体构件

```
public class ConcreteComponent extends Component {
    public void opeate() {
        System.out.println("业务处理");
    }
}
```

* 抽象装饰者

```
public abstract class Decorator extends Component {
    private Component component = null;
    public Decorator(Component component) {
        this.component = component;
    }
    //委托给被装饰者执行
    @Override
    public void operate() {
        this.component.operate();
    }
}
```

* 具体装饰者1

```
public class ConcreteDecorator1 extens Decorator {
    public ConcreteDecorator1(Component component) {
        super(component);
    }
    private void decorate() {
        System.out.println("前置修饰");
    }
    //重写父类Operation
    @Override
    public void operate() {
        this.decorate();
        super.operate();
    }
}
```

* 具体装饰者2

```
public class ConcreteDecorator2 extends Decorator {
    public ConceteDecorator2(Component component) {
        super(component);
    }
    private void decorate() {
        System.out.println("后置修饰");
    }
    //重写父类operation方法
    @Override
    public void operate() {
        super.operate();
        this.decorate();
    }
}
```

* 场景类

```
public class Client {
    public static void main(String[] args) {
        Component component = new ConcreteComponent();
        //加入前置修饰
        component = new ConcreteDecorator1(component);
        //加入后置修饰
        component = new ConcreteDecorator2(component);
        //运行
        component.operate();
    }
}
```

## 优缺点

优点：

* 装饰类和被装饰类不会相互耦合，Component类不需要知道Decorator类，Decorator类也不需要知道具体的构件。
* 装饰模式是继承的一个替代方案，不管装饰多少层，返回的还是Component。并且可以动态的扩展已有类的功能，而继承是静态的增加功能。

缺点：

* 装饰的成熟越多，系统的复杂度越高。

## 装饰者模式与代理模式的区别

装饰者模式实际上是代理模式的一个特殊应用，两者都具有相同的接口，不同的是装饰者模式关注的是对类功能进行加强或减弱，注重类的功能变化，而代理模式着重对代理过程的控制。