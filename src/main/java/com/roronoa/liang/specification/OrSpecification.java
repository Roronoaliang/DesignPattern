package com.roronoa.liang.specification;

/**
 * @Author: RoronoaLiang
 * @Date: 17:08 2017/3/31
 * @Description:
 */
public class OrSpecification extends CompositeSpecification {

    //传递两个规格书
    private IUserSpecification left;
    private IUserSpecification right;

    public OrSpecification(IUserSpecification left, IUserSpecification right) {
        this.left = left;
        this.right = right;
    }

    //进行or操作
    @Override
    public boolean isSatisfiedBy(User user) {
        return left.isSatisfiedBy(user) || right.isSatisfiedBy(user);
    }
}
