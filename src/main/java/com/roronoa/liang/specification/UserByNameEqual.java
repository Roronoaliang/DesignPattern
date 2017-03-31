package com.roronoa.liang.specification;

/**
 * @Author: RoronoaLiang
 * @Date: 17:12 2017/3/31
 * @Description: 姓名相同规格书
 */
public class UserByNameEqual extends  CompositeSpecification{

    //传递查询条件姓名
    private String name;

    public UserByNameEqual(String name) {
        this.name = name;
    }

    @Override
    public boolean isSatisfiedBy(User user) {
        return user.getName().equals(name);
    }
}
