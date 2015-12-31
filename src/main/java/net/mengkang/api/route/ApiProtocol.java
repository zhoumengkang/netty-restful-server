package net.mengkang.api.route;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        if (req.method().equals(HttpMethod.POST) && req instanceof HttpContent){
            System.out.println("post请求");
        }

        decodeRequestParameters();
    }

    private void decodeRequestParameters(){

        final String build      = "build";
        final String appVersion = "appVersion";
        final String channel    = "channel";
        final String api        = "api";
        final String geo        = "geo";
        final String offset     = "offset";
        final String limit      = "limit";
        final String auth       = "auth";

        if (this.parameters.containsKey(build)) {
            try {
                this.build = Integer.parseInt(this.parameters.get(build).get(0));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } finally {
                this.parameters.remove(build);
            }
        }

        if (this.parameters.containsKey(appVersion)) {
            this.appVersion = this.parameters.get(appVersion).get(0);
            this.parameters.remove(appVersion);
        }

        if (this.parameters.containsKey(channel)) {
            this.channel = this.parameters.get(channel).get(0);
            this.parameters.remove(channel);
        }

        if (this.parameters.containsKey(api)) {
            this.api = this.parameters.get(api).get(0);
            this.parameters.remove(api);
        }

        if (this.parameters.containsKey(geo)) {
            this.geo = this.parameters.get(geo).get(0);
            this.parameters.remove(geo);
        }

        if (this.parameters.containsKey(offset)) {
            try {
                this.offset = Integer.parseInt(this.parameters.get(offset).get(0));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } finally {
                this.parameters.remove(offset);
            }
        }

        if (this.parameters.containsKey(limit)) {
            try {
                this.limit = Integer.parseInt(this.parameters.get(limit).get(0));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } finally {
                this.limit = (this.limit > 30) ? 30 : this.limit; // 防止被人一次性请求大量数据
                this.parameters.remove(limit);
            }
        }

        if (this.parameters.containsKey(auth)) {
            this.auth = this.parameters.get(auth).get(0);
            this.parameters.remove(auth);
        }
    }
}
