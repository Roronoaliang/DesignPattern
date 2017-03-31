package com.roronoa.liang.specification;

/**
 * @Author: RoronoaLiang
 * @Date: 17:03 2017/3/31
 * @Description:
 */
public abstract class CompositeSpecification implements  IUserSpecification{
    //是否满足条件由实现类实现
    public abstract  boolean isSatisfiedBy(User user);
    //and操作
    public IUserSpecification and(IUserSpecification spec) {
        return new AndSpecification(this, spec);
    }
    //or操作
    public IUserSpecification or(IUserSpecification spec) {
        return new OrSpecification(this, spec);
    }
    //not操作
    public IUserSpecification not() {
        return new NotSpecification(this);
    }
}
