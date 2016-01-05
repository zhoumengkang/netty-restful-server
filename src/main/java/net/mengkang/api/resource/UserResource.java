package net.mengkang.api.resource;

import net.mengkang.api.bo.UserInfo;
import net.mengkang.api.response.Info;
import net.mengkang.api.response.Result;
import net.mengkang.api.handler.ApiProtocol;
import net.mengkang.api.service.UserService;

public class UserResource extends BaseResource {

    public UserResource(ApiProtocol apiProtocol) {
        super(apiProtocol);
    }

    public Object get() {

        int uid;

        Object uidCheck = parameterIntCheck(apiProtocol, "uid");
        if (uidCheck instanceof Result) {
            return uidCheck;
        } else {
            uid = (int) uidCheck;
        }

        UserService userService = new UserService(apiProtocol);
        UserInfo    userInfo    = new UserInfo(userService.get(uid));

        return new Result<Info>(userInfo);
    }

    public Object post() {
        return success();
    }

    public Object patch() {
        return success();
    }

    public Object delete() {
        return success();
    }

}
