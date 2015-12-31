package net.mengkang.api.route;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

/**
 * 将 http 协议转换为自定义的协议
 */
public class ApiProtocol {

    private static final Logger logger = LoggerFactory.getLogger(ApiHandler.class);

    private int                       build      = 101;
    private String                    appVersion = "1.0";
    private String                    channel    = "mengkang.net";
    private String                    geo        = null;
    private String                    clientIP   = null;
    private String                    serverIP   = null;
    private String                    api        = null;
    private String                    auth       = null;
    private int                       offset     = 0;
    private int                       limit      = 10;
    private HttpMethod                method     = HttpMethod.GET;
    private Map<String, List<String>> parameters = null; // get 和 post 的键值对都存储在这里
    public  byte[]                    postBody   = null; // post 请求时的非键值对内容

    public int getBuild() {
        return build;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getChannel() {
        return channel;
    }

    public String getGeo() {
        return geo;
    }

    public String getClientIP() {
        return clientIP;
    }

    public String getServerIP() {
        return serverIP;
    }

    public String getApi() {
        return api;
    }

    public String getAuth() {
        return auth;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Map<String, List<String>> getParameters() {
        return parameters;
    }

    public byte[] getPostBody() {
        return postBody;
    }

    /**
     * 传输协议初始化
     * 使用 netty 的{@link QueryStringDecoder} 替换之前自己做的简单解析的方法
     * <a href="http://mengkang.net/587.html">http://mengkang.net/587.html</a> 更加全面安全可靠
     *
     * @param ctx
     * @param req
     * @return
     */
    public ApiProtocol(ChannelHandlerContext ctx, HttpRequest req) {
        String uri = req.uri();
        logger.info(uri);

        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri);
        this.parameters = queryStringDecoder.parameters();

        String clientIP = (String) req.headers().get("X-Forwarded-For");
        if (clientIP == null) {
            InetSocketAddress remoteSocket = (InetSocketAddress) ctx.channel().remoteAddress();
            clientIP = remoteSocket.getAddress().getHostAddress();
        }
        this.clientIP = clientIP;

        InetSocketAddress serverSocket = (InetSocketAddress) ctx.channel().localAddress();
        this.serverIP = serverSocket.getAddress().getHostAddress();

        this.method = req.method();

        if (req.method().equals(HttpMethod.POST) && req instanceof HttpContent) {
            System.out.println("post请求");
        }

        decodeRequestParameters();
    }

    public ApiProtocol() {
    }

    /**
     * 解析 queryString
     * 优化笔记 <a href="http://mengkang.net/614.html">http://mengkang.net/614.html</>
     */
    public void decodeRequestParameters() {
        Field[] fields = this.getClass().getDeclaredFields();

        for (int i = 0, length = fields.length; i < length; i++) {
            Field field = fields[i];
            String filedName = field.getName();

            if (filedName.equals("logger")
                    || filedName.equals("method")
                    || filedName.equals("parameters")
                    || filedName.equals("postBody")) {
                continue;
            }

            if (!this.parameters.containsKey(filedName)) {
                continue;
            }

            String fieldType = field.getType().getName();
            field.setAccessible(true);
            try {
                if (fieldType.endsWith("Integer")) {
                    field.set(this, Integer.parseInt(this.parameters.get(filedName).get(0)));
                } else if (fieldType.endsWith("Float")) {
                    field.set(this, Float.parseFloat(this.parameters.get(filedName).get(0)));
                } else if (fieldType.endsWith("Long")) {
                    field.set(this, Long.parseLong(this.parameters.get(filedName).get(0)));
                } else if (fieldType.endsWith("Double")) {
                    field.set(this, Double.parseDouble(this.parameters.get(filedName).get(0)));
                } else {
                    field.set(this, this.parameters.get(filedName).get(0));
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            this.parameters.remove(filedName);
        }
    }
}
