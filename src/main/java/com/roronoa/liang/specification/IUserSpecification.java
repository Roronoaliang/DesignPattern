package com.roronoa.liang.specification;

/**
 * @Author: RoronoaLiang
 * @Date: 16:58 2017/3/31
 * @Description: 规格书接口
 */
public interface IUserSpecification {
    //候选者是否满足要求
    boolean isSatisfiedBy(User user);
    //and操作
    IUserSpecification and(IUserSpecification spec);
    //or操作
    IUserSpecification or(IUserSpecification spec);
    //not操作
    IUserSpecification not();
}
