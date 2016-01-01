package net.mengkang.api.route;

import io.netty.channel.ChannelHandlerContext;
import net.mengkang.api.controller.BaseApi;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 衔接 netty 和 api 控制器
 */
public class ApiHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApiHandler.class);

    /**
     * api 数据输出统一入口和出口
     *
     * @param ctx
     * @param msg
     * @return
     */
    public static byte[] transfer(ChannelHandlerContext ctx, Object msg) {
        ApiProtocol apiProtocol = new ApiProtocol(ctx, msg);

        if (apiProtocol.getApi() == null) {
            return decode(BaseApi.error(BaseApi.API_CAN_NOT_BE_NULL));
        }

        Object result = invoke(apiProtocol.getApi(), apiProtocol);
        if (result == null) {
            return decode(BaseApi.error(BaseApi.UNKNOWN_ERROR));
        }

        return decode(result);
    }

    /**
     * 反射调用 api
     *
     * @param apiName
     * @param apiProtocol
     * @return
     */
    public static Object invoke(String apiName, ApiProtocol apiProtocol) {
        Class<?> classname;
        Method   method;
        Object   result = null;

        Api api = ApiRoute.apiMap.get(apiName);
        if (api == null) {
            return BaseApi.error(BaseApi.API_NOT_FOUND);
        }

        String[] classAndMethod = api.getClassAndMethod();

        if (classAndMethod.length < 2) {
            return BaseApi.error(BaseApi.API_NOT_FOUND);
        }

        if (apiProtocol.getBuild() < api.getBuild()){
            return BaseApi.error(BaseApi.VERSION_IS_TOO_LOW);
        }

        if(!api.getHttpMethod().contains(apiProtocol.getMethod().toString().toLowerCase())){
            return BaseApi.error(BaseApi.REQUEST_MODE_ERROR);
        }

        try {
            classname = Class.forName("net.mengkang.api.controller." + classAndMethod[0]);
        } catch (ClassNotFoundException e) {
            logger.error(e.toString());
            return BaseApi.error(BaseApi.API_NOT_FOUND);
        }

        try {
            method = classname.getMethod(classAndMethod[1], ApiProtocol.class);
        } catch (NoSuchMethodException e) {
            logger.error(e.toString());
            return BaseApi.error(BaseApi.API_NOT_FOUND);
        }

        try {
            result = method.invoke(null, apiProtocol);
        } catch (InvocationTargetException e) {
            logger.error(e.toString());
        } catch (IllegalAccessException e) {
            logger.error(e.toString());
        }

        return result;
    }

    /**
     * 编码输出以及过滤处理
     *
     * @param object
     * @return
     */
    public static byte[] decode(Object object) {
        return new JSONObject(object).toString().getBytes();
    }

}
