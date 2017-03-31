package com.roronoa.liang.singleton;

/**
 * @Author: RoronoaLiang
 * @Date: 19:12 2017/3/31
 * @Description: 懒汉式单例：首次调用getInstance方法时才会实例化，但存在线程安全问题
 */
public class Singleton_Lazy {

    private static Singleton_Lazy instance;
    private Singleton_Lazy() {}
    public static Singleton_Lazy getInstance() {
        if(instance == null) {
            instance = new Singleton_Lazy();
        }
        return instance;
    }
}
