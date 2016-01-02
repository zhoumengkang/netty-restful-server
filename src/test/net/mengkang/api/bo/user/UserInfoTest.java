package net.mengkang.api.bo.user;

import junit.framework.TestCase;
import net.mengkang.api.bo.Info;
import net.mengkang.api.bo.Result;
import net.mengkang.api.entity.User;
import org.json.JSONObject;

/**
 * Created by zhoumengkang on 16/1/2.
 */
public class UserInfoTest extends TestCase {

    private class UserInfo2 extends Info {
        private User user;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

    public void testGetUser() throws Exception {
        UserInfo2 userInfo2 = new UserInfo2();
        userInfo2.setUser(new User(2, "zhou", 25));
        Result result2 = new Result(userInfo2);
        System.out.println(new JSONObject(result2).toString());
        // {"info":{"success":true,"error":0}}
    }
}