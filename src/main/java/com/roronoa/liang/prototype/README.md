## 定义

> 用原型实例指定创建对象的种类，并通过拷贝这些原型创建新的对象。

使用原型模式必须实现Cloneable接口，Cloneable接口只起到标记作用，只有实现了Cloneable接口在JVM中才有可能被拷贝。而真正让对象从有可能被拷贝升级为可以被拷贝则需要覆盖Object中的clone()方法。

## 通用类图

 ![](https://ws1.sinaimg.cn/large/bc18b842gy1fd04okvtgpj20kc0bhjrs)

原型模式中客户端创建对象时不再是直接通过new关键字创建，而是通过调用已有原型类对象的的clone()方法进行复制。

## 代码示例

```
public class PrototypeClass implements Cloneable {
    //覆写Object类中的clone方法
    @Override
    public PrototypeClass clone() {
        PrototypeClass p = null;
        try {
            p = (PrototypeClass)super.clone();
        } catch(CloneNotSupportedException e) {
            //异常处理
        }
        return p;
    }
}
```

## 优缺点

优点：

* 性能优良。原型模式是在内存中进行二进制的拷贝，要比直接new一个对象的性能好很多，特别是要在一个循环体内产生大量的对象时。
* 避免构造函数的约束：使用clone()方法接口进行复制，不需要调用到构造器。

## 使用场景举例

* 类初始化需要消耗非常多的资源，包括数据资源、硬件资源等可以考虑使用原型模式。
* 通过new产生新对象需要非常繁琐的数据准备或者访问权限时可以使用原型模式。
* 一个对象提供给多个调用者，而且各个调用者都需要修改其中某些值时。
* 原型模式经常结合工厂模式使用，通过clone方法创建对象，然后由工厂方法提供给调用者。

## 深克隆和浅克隆

* 深克隆:把要复制的对象所引用的所有对象都进行复制。Java中8种基本数据类型及其对应的包装类型以及String类型在克隆时都是深克隆。

* 浅克隆：对于对象内部的数组、集合、引用对象等都不拷贝，还是指向原生对象的内部地址，换言之对于引用类型仅拷贝引用，副本和原对象共享私有引用对象。如果要将引用类型的克隆变为深克隆需要对引用型成员变量再次克隆。

```
//引用类型成员变量深克隆
public class Thing implements Cloneable {
    private ArrayList<String> arrayList = new ArrayList<String>();
    public Thing clone() {
        Thing thing = null;
        try {
            thing = (Thing)super.clone();
            thing.arrayList = (ArrayList<String)this.arrayList.clone(); //引用类型单独克隆
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return thing;
    }
}
```