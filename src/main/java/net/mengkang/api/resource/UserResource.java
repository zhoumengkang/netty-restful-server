package net.mengkang.api.resource;

import net.mengkang.api.vo.Info;
import net.mengkang.api.vo.ListInfo;
import net.mengkang.api.vo.ListResult;
import net.mengkang.api.vo.Result;
import net.mengkang.api.entity.User;
import net.mengkang.api.handler.ApiProtocol;
import net.mengkang.api.service.UserService;

import java.util.List;

public class UserResource extends BaseResource {

    /*public Object list(ApiProtocol apiProtocol) {

        ListInfo   info       = new ListInfo();
        ListResult listResult = new ListResult(info);

        UserService userService = new UserService(apiProtocol);

        List<User> list = userService.list();

        info.setNum(list.size());
        listResult.setItem(list);

        return listResult;
    }*/

    public class UserInfo extends Info {
        private User user;

        public User getUser() {
            return user;
        }

        public UserInfo(User user) {
            this.user = user;
        }
    }

    public Object get(ApiProtocol apiProtocol) {

        int uid;

        if (apiProtocol.getParameters().containsKey("uid")) {
            try {
                uid = Integer.parseInt(apiProtocol.getParameters().get("uid").get(0));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return error(PARAM_FORMAT_ERROR, "uid");
            }
        } else {
            return error(PARAM_CAN_NOT_BE_NULL, "uid");
        }

        UserService userService = new UserService(apiProtocol);
        UserInfo userInfo    = new UserInfo(userService.get(uid));

        return new Result<Info>(userInfo);
    }
}
