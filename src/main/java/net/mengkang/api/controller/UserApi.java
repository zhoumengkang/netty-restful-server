package net.mengkang.api.controller;

import net.mengkang.api.bo.ListInfo;
import net.mengkang.api.bo.ListResult;
import net.mengkang.api.entity.User;
import net.mengkang.api.route.ApiProtocol;
import net.mengkang.api.service.UserService;

import java.util.List;

/**
 * Created by zhoumengkang on 30/12/15.
 */
public class UserApi extends BaseApi {

    public static Object list(ApiProtocol apiProtocol) {

        ListInfo   info       = new ListInfo();
        ListResult listResult = new ListResult(info);

        UserService userService = new UserService(apiProtocol);
        List<User> list = userService.list();

        info.setNum(list.size());
        listResult.setItem(list);

        return listResult;
    }
}
