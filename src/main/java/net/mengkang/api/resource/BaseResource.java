package net.mengkang.api.resource;

import net.mengkang.api.handler.ApiErrorHandler;
import net.mengkang.api.handler.ApiProtocol;
import net.mengkang.api.vo.Info;
import net.mengkang.api.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * api resource base method
 */
public class BaseResource {

    protected Logger logger;

    public BaseResource() {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    public static Object parameterIntCheck(ApiProtocol apiProtocol,String parameter){
        if (apiProtocol.getParameters().containsKey(parameter)) {
            try {
                return Integer.parseInt(apiProtocol.getParameters().get(parameter).get(0));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return ApiErrorHandler.error(ApiErrorHandler.PARAM_FORMAT_ERROR, parameter);
            }
        } else {
            return ApiErrorHandler.error(ApiErrorHandler.PARAM_CAN_NOT_BE_NULL, parameter);
        }
    }
}
