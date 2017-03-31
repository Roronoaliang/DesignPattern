package com.roronoa.liang.singleton;

/**
 * @Author: RoronoaLiang
 * @Date: 19:19 2017/3/31
 * @Description: 静态内部类实现单例：
 * 　　加载Singleton类时不会初始化静态常量instance从而达到懒加载的效果，同时利用了ClassLoader的机制保证了线程安全。
 * 　　若当前类实现了Serializable序列化接口，就可能被序列化生成多个实例，因为readObject()方法会一直返回一个新的对象
 * 　　解决该问题可以在当前类中添加readResolve()方法
 */
public class Singleton_InternalStaticClass {

    private Singleton_InternalStaticClass() {}

    public static Singleton_InternalStaticClass getInstance() {
        return InnerClass.instance;
    }
    private static class InnerClass {
        public static final Singleton_InternalStaticClass instance = new Singleton_InternalStaticClass();
    }
    //重写readResolve方法解决了反序列化的问题，但无法避免反射产生多个实例
    private Object readResolve() {
        return getInstance();
    }
}
