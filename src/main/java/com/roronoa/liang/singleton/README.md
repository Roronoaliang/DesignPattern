## 定义

单例模式可以说是最简单的设计模式之一，它的定义如下：

> 确保某一个类只有一个实例，并且自行实例化并向整个系统提供该实例。

由于需要保证任何时刻都只能有一个对象，因此**构造器必须是私有**的，不能被外界调用，所以创建全局唯一对象的任务就要交给类自身去做，并且还要**提供一个全局的公共访问点**来获得创建好的实例。

## <spna id="1">饿汉式单例</span>

```
/**
 * 类加载时立即实例化，提前占用系统资源，但是没有线程安全问题
 */
public class Singleton {

    private static final Singleton instance = new Singleton();
    private Singleton() {}

    public static Singleton getInstance() {
        return instance;
    }
}
```

## <span id="2">懒汉式单例</span>

```
/**
 * 首次调用getInstance方法时才会实例化，但存在线程安全问题
 */
public class Singleton {

    private static Singleton instance;
    private Singleton() {}

    public static Singleton getInstance() {
        if(instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

## <span id="3">双重检验锁单例</span>

```
/**
 * 既能达到懒加载效果，也能达到线程安全
 */
public class Singleton {
    //用volatile修饰，保证线程每次使用变量都会读取变量修改后的最新值
    private volatile static Singleton instance;
    private Singleton() {}

    public static Singleton getInstance() {
    //不加下面这一行判断会导致若干个线程耗费等待进入临界区的时间
        if(instance == null) {
            synchronized(Singleton.class) { //静态方法要使用类锁
                if(instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

## <span id="4">内部静态类单例</span>

```
/**
 * 加载Singleton类时不会初始化静态常量instance从而达到懒加载的效果
 * 同时利用了ClassLoader的机制保证了线程安全。
 * 若当前类实现了Serializable序列化接口，就可能被序列化生成多个实例
 * 因为readObject()方法会一直返回一个新的对象
 * 解决该问题可以在当前类中添加readResolve()方法
 */
public class Singleton {

    private Singleton() {}

    public static Singleton getInstance() {
        return InnerClass.instance;
    }

    private static class InnerClass() {
        public static final Singleton instance = new Singleton();
    }

    //重写readResolve方法解决了反序列化的问题，但无法避免反射产生多个实例
	private Object readResolve() {
		return getInstance();
	}
}
```

## <span id="5">枚举类单例</span>

```
/**
 *基于枚举实现单例模式，JDK1.5后才能使用;
 *能避免线程安全问题，也能防止反序列化、反射重新创建新的对象
 */
public enum Singleton {
    instance;
    //other methods
}
```

&emsp;&emsp;以上五种方式中前四种都能通过反射机制获得新对象实例，从而破坏单例条件，而如果实现了Serializable接口，还能通过反序列化获得新对象实例，以下是利用反射获得新对象的实例代码。

```
public class Test {
    public static void main(String[] args) {
        Class c = Class.forName("Singleton");//类的全限定名
        Constructor con = c.getDeclaredConstructor();
		con.setAccessible(true);
		System.out.println(con.newInstance() == con.newInstance());
    }
}
```

## 单例模式的优缺点

优点：

* 单例模式在内存中只有一个实例，减少了内存开销，特别是当一个对象需要频繁的创建、销毁时。
* 当一个对象的产生需要消耗较多资源时，通过启动时生成单例对象，然后永驻内存的方式能减少系统的性能开销。
* 单例模式可以避免对资源的多重占用。
* 单例模式可以在系统设置全局访问点，优化和共享资源访问。

缺点：

* 单例模式一般没有接口，扩展困难。
* 单例模式如果没有完成，不能进行测试工作，不利于测试工作的并行进行。

## 单例模式的使用场景举例

* 要求生成唯一序列号的环境。
* 在整个项目中需要一个共享访问点或共享数据。
* 创建一个对象需要消耗的资源过多，如要访问IO和数据库等资源。

## 注意事项

使用单例模式需要注意的一点就是JVM的垃圾回收机制，如果一个单例对象在内存中长久不使用，JVM会认为该对象是一个垃圾从而清理掉，等下次再调用时就会重新产生一个对象，如果在应用中使用单例类管理有状态的变量，可能会出现恢复原状的情况，从而导致故障。此时有两种解决思路：

* 由容器管理单例的生命周期，如Java EE容器或Spring容器可以让对象长驻内存。
* 使用异步记录或者观测者模式记录状态值的变化，将状态值随时持久化，确保即使单例对象重新初始化也可以从资源环境获得销毁前的数据。