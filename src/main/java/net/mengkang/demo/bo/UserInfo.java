package net.mengkang.demo.bo;

import net.mengkang.demo.entity.User;
import net.mengkang.nettyrest.response.Info;

/**
 * Created by zhoumengkang on 5/1/16.
 */
public class UserInfo extends Info {
    private User user;

    public User getUser() {
        return user;
    }

    public UserInfo(User user) {
        this.user = user;
    }
}