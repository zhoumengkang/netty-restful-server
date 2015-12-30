package net.mengkang.api.route;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhoumengkang on 30/12/15.
 */
public class ApiRoute {
    public static final Map<String, String> apiMap = new HashMap<String, String>();

    static {
        apiMap.put("test","UserApi.list");
    }
}
