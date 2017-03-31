package com.roronoa.liang.specification;

import java.util.ArrayList;

/**
 * @Author: RoronoaLiang
 * @Date: 17:16 2017/3/31
 * @Description: 用户操作接口
 */
public interface IUserProvider {
    //根据指定规格书查询用户
    ArrayList<User> findUser(IUserSpecification spec);
}
