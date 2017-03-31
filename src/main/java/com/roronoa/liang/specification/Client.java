package com.roronoa.liang.specification;

import java.util.ArrayList;

/**
 * @Author: RoronoaLiang
 * @Date: 17:20 2017/3/31
 * @Description:
 */
public class Client {
    public static void main(String[] args) {
        //mock用户集合
        ArrayList<User> users = mockUser();
        //定义用户查询类
        IUserProvider provider = new UserProvider(users);
        //查询姓名为张三的用户
        ArrayList<User> result = provider.findUser(new UserByNameEqual("张三"));
        //打印结果
        System.out.println("姓名为张三的用户");
        printUser(result);
        //查询年龄大于20的用户
        result = provider.findUser(new UserByAgeThan(20));
        //打印结果
        System.out.println("年龄大于20的用户:");
        printUser(result);
        //查询姓名为张三并且年龄大于30的用户
        result = provider.findUser(new AndSpecification(new UserByNameEqual("张三"), new UserByAgeThan(30)));
        //打印结果
        System.out.println("姓名为张三并且年龄大于30的用户:");
        printUser(result);
        //查询年龄不大于20岁的用户
        result = provider.findUser(new NotSpecification(new UserByAgeThan(20)));
        System.out.println("年龄不大于20岁的用户:");
        printUser(result);
    }

    public static ArrayList<User> mockUser() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("小明", 10));
        users.add(new User("小刚", 20));
        users.add(new User("小红", 30));
        users.add(new User("张三", 33));
        users.add(new User("张三", 18));
        users.add(new User("李四", 23));
        return users;
    }

    private static void printUser(ArrayList<User> result) {
        if (result != null) {
            for (User u : result) {
                System.out.println(u);
            }
        }
    }
}