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
        ListResult listResult = new ListResult();

        ListInfo   info = new ListInfo();
        List<User> list = UserService.list();
        info.setNum(list.size());
        listResult.setItem(list);
        listResult.setInfo(info);
        return listResult;
    }
}
