package net.mengkang.api.vo.user;

import net.mengkang.api.entity.User;
import net.mengkang.api.vo.Info;

public class UserInfo extends Info {
    private User user;

    public User getUser() {
        return user;
    }

    public UserInfo(User user) {
        this.user = user;
    }
}