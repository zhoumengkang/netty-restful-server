package net.mengkang.demo.resource;


import net.mengkang.demo.entity.User;
import net.mengkang.nettyrest.ApiProtocol;
import net.mengkang.demo.service.UserService;
import net.mengkang.nettyrest.response.ListInfo;
import net.mengkang.nettyrest.response.ListResult;
import net.mengkang.nettyrest.BaseResource;
import net.mengkang.nettyrest.response.Result;

import java.util.List;

public class UsersResource extends BaseResource {

    public UsersResource(ApiProtocol apiProtocol) {
        super(apiProtocol);
    }

    public Result get() {

        ListInfo   info       = new ListInfo();
        ListResult listResult = new ListResult(info);

        UserService userService = new UserService(apiProtocol);

        List<User> list = userService.list();

        info.setNum(list.size());
        listResult.setItem(list);

        return listResult;
    }
}
