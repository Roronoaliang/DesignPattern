package com.roronoa.liang.singleton;

/**
 * @Author: RoronoaLiang
 * @Date: 19:16 2017/3/31
 * @Description: 饿汉式单例：类加载时立即实例化，提前占用系统资源，但是没有线程安全问题
 */
public class Singleton_Hungry {

    private static final Singleton_Hungry instance = new Singleton_Hungry();

    private Singleton_Hungry() {
    }

    public static Singleton_Hungry getInstance() {
        return instance;
    }
}
