package net.mengkang.api.resource;

import net.mengkang.api.bo.UserInfo;
import net.mengkang.api.response.Info;
import net.mengkang.api.response.Result;
import net.mengkang.api.handler.ApiProtocol;
import net.mengkang.api.service.UserService;

public class UserResource extends BaseResource {

    public Object get(ApiProtocol apiProtocol) {

        int uid;

        Object uidCheck = parameterIntCheck(apiProtocol, "uid");
        if (uidCheck instanceof Result) {
            return uidCheck;
        } else {
            uid = (int) uidCheck;
        }

        UserService userService = new UserService(apiProtocol);
        UserInfo userInfo    = new UserInfo(userService.get(uid));

        return new Result<Info>(userInfo);
    }
}
