package com.roronoa.liang.specification;

/**
 * @Author: RoronoaLiang
 * @Date: 17:06 2017/3/31
 * @Description:
 */
public class AndSpecification extends CompositeSpecification {

    //传递两个规格书进行and操作
    private IUserSpecification left;
    private IUserSpecification right;

    public AndSpecification(IUserSpecification left, IUserSpecification right) {
        this.left = left;
        this.right = right;
    }

    //进行and运算
    @Override
    public boolean isSatisfiedBy(User user) {
        return left.isSatisfiedBy(user) && right.isSatisfiedBy(user);
    }
}
