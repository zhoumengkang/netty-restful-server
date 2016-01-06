package net.mengkang.api.resource;

import net.mengkang.api.bo.UserCreateSuccess;
import net.mengkang.api.bo.UserInfo;
import net.mengkang.api.response.Info;
import net.mengkang.api.response.ResponseHandler;
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
        UserCreateSuccess userCreateSuccess = new UserCreateSuccess();
        userCreateSuccess.setId(2);
        userCreateSuccess.setUrl("http://netty.restful.api.mengkang.net/user/2");
        userCreateSuccess.setCode(ResponseHandler.CREATED_SUCCESS);
        return new Result<>(userCreateSuccess);
    }

    public Object patch() {
        return success(202);
    }

    public Object delete() {
        return success(203);
    }

}
