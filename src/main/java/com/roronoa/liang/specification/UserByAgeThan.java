package com.roronoa.liang.specification;

/**
 * @Author: RoronoaLiang
 * @Date: 17:13 2017/3/31
 * @Description:
 */
public class UserByAgeThan extends CompositeSpecification {

    //传递查询条件年龄
    private int age;

    public UserByAgeThan(int age) {
        this.age = age;
    }

    //判断年龄是否大于20
    @Override
    public boolean isSatisfiedBy(User user) {
        return user.getAge() > age;
    }
}
