package net.mengkang.nettyrest;

import net.mengkang.nettyrest.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * api resource base method
 */
public class BaseResource {

    protected Logger logger;

    protected ApiProtocol apiProtocol;

    public BaseResource(ApiProtocol apiProtocol) {
        this.logger = LoggerFactory.getLogger(this.getClass());;
        this.apiProtocol = apiProtocol;
    }

    public Object parameterIntCheck(ApiProtocol apiProtocol, String parameter) {
        if (apiProtocol.getParameters().containsKey(parameter)) {
            try {
                return Integer.parseInt(apiProtocol.getParameters().get(parameter).get(0));
            } catch (NumberFormatException e) {
                logger.error(e.getMessage());
                return error(ResponseHandler.PARAM_FORMAT_ERROR, parameter);
            }
        } else {
            return error(ResponseHandler.PARAM_CAN_NOT_BE_NULL, parameter);
        }
    }

    protected Object error(int code) {
        return ResponseHandler.error(code);
    }

    protected Result error(int code, String parameter) {
        return ResponseHandler.error(code, parameter);
    }

    protected Result success() {
        return ResponseHandler.success();
    }

    protected Object success(int code) {
        return ResponseHandler.success(code);
    }

}
