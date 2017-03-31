## 定义

> 将一个类的接口变成客户端所期待的另一种接口，从而使原本因接口不匹配而无法在一起工作的两个类能够一起工作。

## 通用类图

![](https://ws1.sinaimg.cn/large/bc18b842gy1fd761xsyqhj20q70aawev)

![](https://ws1.sinaimg.cn/large/bc18b842gy1fd76296x4ij20pu09z74r)

* Target：目标角色，定义将其他类转换成什么接口。
* Adaptee：被适配角色，它是已经存在，运行良好的对象，经过适配器转换为目标角色供客户端使用。
* Adapter：适配器角色，Target和Adaptee都是已经存在的角色，而适配器则是需要新建立的，一般采用继承或类关联的方式来适配Adaptee和Target。

适配者模式可以有两种写法：类适配器和对象适配器。类适配器是类间继承，对象适配器是对象的合成关系。因为对象适配器是通过类间的关联关系进行耦合的，所以更加灵活，而类适配器就只能通过覆写源角色的方法进行扩展，实际上较少采用。

## 代码示例

* 目标角色

```
public interface Target {
    public void request();
}
```

* 目标角色实现类

```
public class ConcreteTarget implements Target {
    public void request() {
        System.out.println("现役目标角色");
    }
}
```

* 被适配者

```
public class Adaptee {
    public void specialRequest() {
        System.out.println("现役源角色");
    }
}
```

* 适配器角色

```
public class Adapter extends Adaptee implements Target {
    public void request() {
        super.specialRequest();
    }
}
```

* 场景类

```
public class Client {
    public static void main(String[] args) {
    //调用原有的目标角色
    Target target = new ConcreteTarget();
    target.request();
    //通过适配器调用被适配对象
    Target target2 = new Adapter();
    target2.request();
    }
}
```

## 优缺点

优点：

* 适配器模式可以让两个没有任何关系的类在一起运行。
* 增加了类的透明性，客户端只需要访问Target和适配器即可。
* 提高了类的复用度，不需要修改源角色来适应新业务。
* 灵活性好，适配器不对原有系统有任何影响。

缺点：

* 适配器是一个补救模式，不是用来解决设计阶段和处于开发阶段的问题。
