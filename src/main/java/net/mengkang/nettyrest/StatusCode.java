package net.mengkang.nettyrest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhoumengkang on 7/1/16.
 */
public class StatusCode {

    public static final Map<Integer, String> codeMap = new HashMap<>();

    public static final int CREATED_SUCCESS       = 201;
    public static final int UNKNOWN_ERROR         = 1000;
    public static final int API_NOT_FOUND         = 1001;
    public static final int API_CAN_NOT_BE_NULL   = 1002;
    public static final int PARAM_FORMAT_ERROR    = 1003;
    public static final int PARAM_CAN_NOT_BE_NULL = 1004;
    public static final int VERSION_IS_TOO_LOW    = 1005;
    public static final int REQUEST_MODE_ERROR    = 1006;
    public static final int API_SERVER_ERROR      = 1007;

    static {
        codeMap.put(CREATED_SUCCESS, "created success");
        codeMap.put(UNKNOWN_ERROR, "unknown error");
        codeMap.put(API_NOT_FOUND, "the api can't be found");
        codeMap.put(API_CAN_NOT_BE_NULL, "can't request without a api name");
        codeMap.put(PARAM_FORMAT_ERROR, "param : %s format error");
        codeMap.put(PARAM_CAN_NOT_BE_NULL, "param : %s can't be null");
        codeMap.put(VERSION_IS_TOO_LOW, "version is too low, please update your client");
        codeMap.put(REQUEST_MODE_ERROR, "the http request method is not allow");
        codeMap.put(API_SERVER_ERROR, "api server error");
    }
}
