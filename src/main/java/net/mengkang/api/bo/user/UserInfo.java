package net.mengkang.api.bo.user;

import net.mengkang.api.bo.Info;
import net.mengkang.api.entity.User;

/**
 * Created by zhoumengkang on 16/1/2.
 */
public class UserInfo extends Info {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}