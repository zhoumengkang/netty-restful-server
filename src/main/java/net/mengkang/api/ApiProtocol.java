package net.mengkang.api;

import java.util.List;
import java.util.Map;

/**
 * Created by zhoumengkang on 30/12/15.
 */
public class ApiProtocol {

    public int build = 101;

    public String appVersion = "1.0";

    public String channel = "mengkang.net";

    public String geo = null;

    public String clientIP = null;

    public String serverIP = null;

    public String api = null;

    public String auth = null;

    public int offset = 0;

    public int limit = 10;

    public Map<String, List<String>> parameters;

    public String method = "get";
}
