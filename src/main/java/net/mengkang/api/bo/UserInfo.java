package net.mengkang.api.bo;

import net.mengkang.api.entity.User;
import net.mengkang.api.response.Info;

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