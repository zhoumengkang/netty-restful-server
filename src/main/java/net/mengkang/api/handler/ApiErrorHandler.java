package net.mengkang.api.handler;

import net.mengkang.api.vo.Info;
import net.mengkang.api.vo.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhoumengkang on 5/1/16.
 */
public class ApiErrorHandler {
    public static final Map<Integer, String> errors = new HashMap<>();

    public static final int UNKNOWN_ERROR         = 1000;
    public static final int API_NOT_FOUND         = 1001;
    public static final int API_CAN_NOT_BE_NULL   = 1002;
    public static final int PARAM_FORMAT_ERROR    = 1003;
    public static final int PARAM_CAN_NOT_BE_NULL = 1004;
    public static final int VERSION_IS_TOO_LOW    = 1005;
    public static final int REQUEST_MODE_ERROR    = 1006;

    static {
        errors.put(UNKNOWN_ERROR, "unknown error");
        errors.put(API_NOT_FOUND, "the api can't be found");
        errors.put(API_CAN_NOT_BE_NULL, "can't request without a api name");
        errors.put(PARAM_FORMAT_ERROR, "param : %s format error");
        errors.put(PARAM_CAN_NOT_BE_NULL, "param : %s can't be null");
        errors.put(VERSION_IS_TOO_LOW, "version is too low, please update your client");
        errors.put(REQUEST_MODE_ERROR, "the http request method is not allow");
    }

    public static Object error(int errorCode) {
        Result result = new Result<>(new Info());
        result.getInfo().setError(errorCode).setErrorMessage(errors.get(errorCode));
        return result;
    }

    /**
     * error for {@link #PARAM_FORMAT_ERROR} {@link #PARAM_CAN_NOT_BE_NULL}
     *
     * @param parameter
     * @return
     */
    public static Result error(int errorCode,String parameter) {
        Result result = new Result<>(new Info());
        result.getInfo()
                .setError(errorCode)
                .setErrorMessage(String.format(errors.get(errorCode), parameter));
        return result;
    }
}
