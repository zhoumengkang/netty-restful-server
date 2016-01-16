package net.mengkang.demo.service;

import net.mengkang.demo.dao.UserDao;
import net.mengkang.demo.entity.User;
import net.mengkang.nettyrest.ApiProtocol;

import java.util.ArrayList;
import java.util.List;

public class UserService extends BaseService{

    public UserService(ApiProtocol apiProtocol) {
        super(apiProtocol);
    }

    /**
     * Version compatibility demo
     * @param uid
     * @return
     */
    public User get(int uid){

        if (apiProtocol.getBuild() > 105){

            System.out.println(new UserDao().getName(uid));
        }

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
