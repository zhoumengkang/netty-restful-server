package net.mengkang.api.service;

import net.mengkang.api.entity.User;
import net.mengkang.api.route.ApiProtocol;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoumengkang on 31/12/15.
 */
public class UserService extends BaseService{

    public UserService(ApiProtocol apiProtocol) {
        super(apiProtocol);
    }

    public User get(int uid){
        return new User(uid,"xxx",25);
    }

    public List<User> list(){
        List<User> users = new ArrayList<User>();
        users.add(new User(1,"mengkang.zhou",25));
        users.add(new User(2,"meng.zhou",23));

        if (apiProtocol.getBuild() > 102) {
            users.add(new User(3,"zhou.mengkang",24));
        }

        return users;
    }
}
