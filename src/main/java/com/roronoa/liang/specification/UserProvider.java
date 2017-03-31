package com.roronoa.liang.specification;

import java.util.ArrayList;

/**
 * @Author: RoronoaLiang
 * @Date: 17:17 2017/3/31
 * @Description:
 */
public class UserProvider implements IUserProvider {

    //传递用户列表
    private ArrayList<User> users;

    public UserProvider(ArrayList<User> users) {
        this.users = users;
    }

    //根据具体的规格书查找用户
    @Override
    public ArrayList<User> findUser(IUserSpecification spec) {
        ArrayList<User> result = new ArrayList<>();
        for(User u : users) {
            if(spec.isSatisfiedBy(u)){
                result.add(u);
            }
        }
        return result;
    }
}
