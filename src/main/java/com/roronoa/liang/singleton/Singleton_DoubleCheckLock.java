package com.roronoa.liang.singleton;

/**
 * @Author: RoronoaLiang
 * @Date: 19:18 2017/3/31
 * @Description: 双重检验锁单例：既能达到懒加载效果，也能达到线程安全
 */
public class Singleton_DoubleCheckLock {
    //用volatile修饰，保证线程每次使用变量都会读取变量修改后的最新值
    private volatile static Singleton_DoubleCheckLock instance;

    private Singleton_DoubleCheckLock() {
    }

    public static Singleton_DoubleCheckLock getInstance() {
        //不加下面这一行判断会导致若干个线程耗费等待进入临界区的时间
        if (instance == null) {
            synchronized (Singleton_DoubleCheckLock.class) { //静态方法要使用类锁
                if (instance == null) {
                    instance = new Singleton_DoubleCheckLock();
                }
            }
        }
        return instance;
    }
}
