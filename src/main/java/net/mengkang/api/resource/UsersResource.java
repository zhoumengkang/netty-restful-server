package net.mengkang.api.resource;


import net.mengkang.api.bo.UserCreateSuccess;
import net.mengkang.api.entity.User;
import net.mengkang.api.handler.ApiProtocol;
import net.mengkang.api.response.ResponseHandler;
import net.mengkang.api.response.Result;
import net.mengkang.api.service.UserService;
import net.mengkang.api.response.ListInfo;
import net.mengkang.api.response.ListResult;

import java.util.List;

public class UsersResource extends BaseResource {

    public UsersResource(ApiProtocol apiProtocol) {
        super(apiProtocol);
    }

    public Object get() {

        ListInfo   info       = new ListInfo();
        ListResult listResult = new ListResult(info);

        UserService userService = new UserService(apiProtocol);

        List<User> list = userService.list();

        info.setNum(list.size());
        listResult.setItem(list);

        return listResult;
    }

    public Object post(){
        UserCreateSuccess userCreateSuccess = new UserCreateSuccess();
        userCreateSuccess.setId(2);
        userCreateSuccess.setUrl("http://netty.restful.api.mengkang.net/user/2");
        userCreateSuccess.setCode(ResponseHandler.CREATED_SUCCESS);
        return new Result<>(userCreateSuccess);
    }
}
