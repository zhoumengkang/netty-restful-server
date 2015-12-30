package net.mengkang.api;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhoumengkang on 30/12/15.
 */
public class ApiHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApiHandler.class);

    /**
     * api 数据输出统一出口
     * @param ctx
     * @param httpRequest
     * @return
     */
    public static byte[] transfer(ChannelHandlerContext ctx, HttpRequest httpRequest) {
        ApiProtocol apiProtocol = apiProtocolInitializer(ctx, httpRequest);
        if (apiProtocol.api == null) {
            return "error".getBytes();
        }
        return new JSONObject(invoke(apiProtocol.api)).toString().getBytes();
    }

    public static ApiProtocol apiProtocolInitializer(ChannelHandlerContext ctx, HttpRequest httpRequest) {
        String uri = httpRequest.uri();
        logger.info(uri);

        // 使用 netty 自带的替换之前自己做的简单解析的方法 http://mengkang.net/587.html
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri);
        Map<String, List<String>> parameters  = queryStringDecoder.parameters();

        ApiProtocol apiProtocol = new ApiProtocol();

        String clientIP = (String) httpRequest.headers().get("X-Forwarded-For");
        if (clientIP == null) {
            InetSocketAddress remoteSocket = (InetSocketAddress) ctx.channel().remoteAddress();
            clientIP = remoteSocket.getAddress().getHostAddress();
        }
        apiProtocol.clientIP = clientIP;

        InetSocketAddress serverSocket = (InetSocketAddress) ctx.channel().localAddress();
        apiProtocol.serverIP = serverSocket.getAddress().getHostAddress();

        final String build      = "build";
        final String appVersion = "appVersion";
        final String channel    = "channel";
        final String api        = "api";
        final String geo        = "geo";
        final String offset     = "offset";
        final String limit      = "limit";
        final String auth       = "auth";
        final String method     = "method";

        if (httpRequest.method().equals(HttpMethod.POST)) {

        }


        if (parameters.containsKey(build)) {
            try {
                apiProtocol.build = Integer.parseInt(parameters.get(build).get(0));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } finally {
                parameters.remove(build);
            }
        }

        if (parameters.containsKey(appVersion)) {
            apiProtocol.appVersion = parameters.get(appVersion).get(0);
            parameters.remove(appVersion);
        }

        if (parameters.containsKey(channel)) {
            apiProtocol.channel = parameters.get(channel).get(0);
            parameters.remove(channel);
        }

        if (parameters.containsKey(api)) {
            apiProtocol.api = parameters.get(api).get(0);
            parameters.remove(api);
        }

        if (parameters.containsKey(geo)) {
            apiProtocol.geo = parameters.get(geo).get(0);
            parameters.remove(geo);
        }

        if (parameters.containsKey(offset)) {
            try {
                apiProtocol.offset = Integer.parseInt(parameters.get(offset).get(0));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } finally {
                parameters.remove(offset);
            }
        }

        if (parameters.containsKey(limit)) {
            try {
                apiProtocol.limit = Integer.parseInt(parameters.get(limit).get(0));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } finally {
                if (apiProtocol.limit > 30) {
                    apiProtocol.limit = 30; // 防止被人一次性请求大量数据
                }
                parameters.remove(limit);
            }
        }

        if (parameters.containsKey(auth)) {
            apiProtocol.auth = parameters.get(auth).get(0);
            parameters.remove(auth);
        }



        return apiProtocol;
    }

    public static Map<String, Object> invoke(String method) {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("info", "helloWorld");
        return res;
    }


}
