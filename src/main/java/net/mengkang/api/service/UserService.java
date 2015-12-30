package net.mengkang.api.service;

import net.mengkang.api.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoumengkang on 31/12/15.
 */
public class UserService {

    public static List<User> list(){
        List<User> users = new ArrayList<User>();
        users.add(new User(1,"mengkang.zhou",25));
        return users;
    }
}
