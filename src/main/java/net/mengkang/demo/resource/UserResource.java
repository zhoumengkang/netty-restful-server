package net.mengkang.demo.resource;

import net.mengkang.demo.bo.UserCreateSuccess;
import net.mengkang.demo.bo.UserInfo;
import net.mengkang.nettyrest.StatusCode;
import net.mengkang.nettyrest.response.Info;
import net.mengkang.nettyrest.response.Result;
import net.mengkang.nettyrest.ApiProtocol;
import net.mengkang.demo.service.UserService;
import net.mengkang.nettyrest.BaseResource;

public class UserResource extends BaseResource {

    public UserResource(ApiProtocol apiProtocol) {
        super(apiProtocol);
    }

    public Result get() {

        int uid;

        Object uidCheck = parameterIntCheck(apiProtocol, "uid");
        if (uidCheck instanceof Result) {
            return (Result) uidCheck;
        } else {
            uid = (int) uidCheck;
        }

        UserService userService = new UserService(apiProtocol);
        UserInfo    userInfo    = new UserInfo(userService.get(uid));

        return new Result<Info>(userInfo);
    }

    public Result post() {
        UserCreateSuccess userCreateSuccess = new UserCreateSuccess();
        userCreateSuccess.setId(2);
        userCreateSuccess.setUrl("http://netty.restful.api.mengkang.net/user/2");
        userCreateSuccess.setCode(StatusCode.CREATED_SUCCESS);
        return new Result<>(userCreateSuccess);
    }

    public Result patch() {
        return success(202);
    }

    public Result delete() {
        return success(203);
    }

}
