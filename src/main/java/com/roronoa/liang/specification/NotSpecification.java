package com.roronoa.liang.specification;

/**
 * @Author: RoronoaLiang
 * @Date: 17:10 2017/3/31
 * @Description:
 */
public class NotSpecification extends CompositeSpecification{

    //传递一个规格书
    private IUserSpecification spec;

    public NotSpecification(IUserSpecification spec) {
        this.spec = spec;
    }

    //进行not操作
    @Override
    public boolean isSatisfiedBy(User user) {
        return !spec.isSatisfiedBy(user);
    }
}
