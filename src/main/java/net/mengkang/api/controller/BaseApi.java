package net.mengkang.api.controller;

import net.mengkang.api.bo.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhoumengkang on 30/12/15.
 */
public class BaseApi {

    public static final Map<Integer, String> errors = new HashMap<Integer, String>();

    public static final int UNKNOWN_ERROR       = 1000;
    public static final int API_NOT_FOUND       = 1001;
    public static final int API_CAN_NOT_BE_NULL = 1002;
    public static final int PARAM_FORMAT_ERROR  = 1003;

    static {
        errors.put(UNKNOWN_ERROR, "未知错误");
        errors.put(API_NOT_FOUND, "该 api 不存在");
        errors.put(API_CAN_NOT_BE_NULL, "api 不能为空");
        errors.put(PARAM_FORMAT_ERROR, "参数格式不正确");
    }

    public static Object error(int errorCode) {
        Result result = new Result();
        result.getInfo().setError(errorCode).setErrorMessage(errors.get(errorCode));
        return result;
    }
}
