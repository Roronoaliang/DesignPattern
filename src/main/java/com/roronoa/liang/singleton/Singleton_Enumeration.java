package com.roronoa.liang.singleton;

/**
 * @Author: RoronoaLiang
 * @Date: 19:24 2017/3/31
 * @Description:  枚举类单例:基于枚举实现单例模式，JDK1.5后才能使用;能避免线程安全问题，也能防止反序列化、反射重新创建新的对象
 */
public enum Singleton_Enumeration {
    instance;
    //other methods
}
