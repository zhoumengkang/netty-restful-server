package net.mengkang.api.resource;


import net.mengkang.api.entity.User;
import net.mengkang.api.handler.ApiProtocol;
import net.mengkang.api.service.UserService;
import net.mengkang.api.response.ListInfo;
import net.mengkang.api.response.ListResult;

import java.util.List;

public class UsersResource extends BaseResource {

    public Object get(ApiProtocol apiProtocol) {

        ListInfo   info       = new ListInfo();
        ListResult listResult = new ListResult(info);

        UserService userService = new UserService(apiProtocol);

        List<User> list = userService.list();

        info.setNum(list.size());
        listResult.setItem(list);

        return listResult;
    }
}
