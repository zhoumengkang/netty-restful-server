package net.mengkang.api.controller;

import net.mengkang.api.vo.Info;
import net.mengkang.api.vo.ListInfo;
import net.mengkang.api.vo.ListResult;
import net.mengkang.api.vo.Result;
import net.mengkang.api.vo.user.UserInfo;
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

    public static Object get(ApiProtocol apiProtocol){

        int uid = 0;

        if (apiProtocol.getParameters().containsKey("uid")){
            try {
                uid = Integer.parseInt(apiProtocol.getParameters().get("uid").get(0));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return error(PARAM_FORMAT_ERROR,"uid");
            }
        }else{
            return error(PARAM_CAN_NOT_BE_NULL,"uid");
        }

        UserService userService = new UserService(apiProtocol);

        UserInfo userInfo = new UserInfo();
        userInfo.setUser(userService.get(uid));

        return new Result<Info>(userInfo);
    }
}
