package net.mengkang.api.route;

import io.netty.channel.ChannelHandlerContext;
import net.mengkang.api.controller.BaseApi;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * connect netty server and api controller
 *
 */
public class ApiHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApiHandler.class);

    /**
     * api data I/O port
     *
     * @param ctx
     * @param msg
     * @return
     */
    public static byte[] transfer(ChannelHandlerContext ctx, Object msg) {
        ApiProtocol apiProtocol = new ApiProtocol(ctx, msg);

        if (apiProtocol.getApi() == null) {
            return encode(BaseApi.error(BaseApi.API_CAN_NOT_BE_NULL));
        }

        Object result = invoke(apiProtocol.getApi(), apiProtocol);
        if (result == null) {
            return encode(BaseApi.error(BaseApi.UNKNOWN_ERROR));
        }

        return encode(result);
    }

    /**
     * invoke api controller method by apiName, but the request apiProtocol should observe routeMap regulations
     *
     * @param apiName
     * @param apiProtocol
     * @return
     */
    public static Object invoke(String apiName, ApiProtocol apiProtocol) {
        Class<?> classname;
        Object   classObject;
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
            classObject = classname.newInstance();
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
            return BaseApi.error(BaseApi.API_NOT_FOUND);
        } catch (InstantiationException e) {
            logger.error(e.getMessage());
            return BaseApi.error(BaseApi.API_NOT_FOUND);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
            return BaseApi.error(BaseApi.API_NOT_FOUND);
        }

        try {
            method = classname.getMethod(classAndMethod[1], ApiProtocol.class);
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage());
            return BaseApi.error(BaseApi.API_NOT_FOUND);
        }

        try {
            result = method.invoke(classObject, apiProtocol);
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error(e.toString());
        }

        return result;
    }

    /**
     * exchange the api controller returns to a JSONObject
     *
     * @param object
     * @return
     */
    public static byte[] encode(Object object) {
        String data = new JSONObject(object).toString();
        data = filter(data);
        return data.getBytes();
    }

    /**
     * we always need filter something for some reason,
     * otherwise we can replace the timestamp to the string we defined, and so on.
     *
     * @param data
     * @return
     */
    public static String filter(String data){
        return data;
    }

}
