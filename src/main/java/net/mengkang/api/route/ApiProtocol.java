package net.mengkang.api.route;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MemoryAttribute;
import io.netty.handler.codec.http.multipart.MixedAttribute;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.www.http.HttpClient;

import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * convert the netty HttpRequest object to a api protocol object
 *
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
    private Map<String, List<String>> parameters = new HashMap<String, List<String>>(); // get 和 post 的键值对都存储在这里
    public  String                    postBody   = null; // post 请求时的非键值对内容

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

    public String getPostBody() {
        return postBody;
    }

    /**
     * api protocol object initializer
     *
     * @param ctx
     * @param msg
     * @return
     */
    public ApiProtocol(ChannelHandlerContext ctx, Object msg) {
        HttpRequest req = (HttpRequest) msg;

        String clientIP = (String) req.headers().get("X-Forwarded-For");
        if (clientIP == null) {
            InetSocketAddress remoteSocket = (InetSocketAddress) ctx.channel().remoteAddress();
            clientIP = remoteSocket.getAddress().getHostAddress();
        }
        this.clientIP = clientIP;

        InetSocketAddress serverSocket = (InetSocketAddress) ctx.channel().localAddress();
        this.serverIP = serverSocket.getAddress().getHostAddress();

        this.method = req.method();

        queryStringHandler(req);
        requestParametersHandler(req);
        requestBodyHandler(msg);

        if (this.parameters.size() == 0) {
            return;
        }

        setFields();

    }

    /**
     * set this class field's value by {@link #parameters}
     *
     * the code improved log in my blog <a href="http://mengkang.net/614.html">http://mengkang.net/614.html</>
     */
    public void setFields() {
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
                if (fieldType.endsWith("int")) {
                    field.set(this, Integer.parseInt(this.parameters.get(filedName).get(0)));
                } else if (fieldType.endsWith("float")) {
                    field.set(this, Float.parseFloat(this.parameters.get(filedName).get(0)));
                } else if (fieldType.endsWith("long")) {
                    field.set(this, Long.parseLong(this.parameters.get(filedName).get(0)));
                } else if (fieldType.endsWith("double")) {
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

    /**
     * queryString decode, put all the query key value in {@link #parameters}
     *
     * use netty's {@link QueryStringDecoder} replace my bad decode method <a href="http://mengkang.net/587.html">http://mengkang.net/587.html</a>
     *
     * @param req
     */
    public void queryStringHandler(HttpRequest req){
        String uri = req.uri();
        logger.info(uri);

        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri);
        if (queryStringDecoder.parameters().size() > 0) {
            this.parameters = queryStringDecoder.parameters();
        }
    }

    /**
     * request parameters put in {@link #parameters}
     *
     * @param req
     */
    public void requestParametersHandler(HttpRequest req){
        if (req.method().equals(HttpMethod.POST)) {
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(req);
            try{
                List<InterfaceHttpData> postList = decoder.getBodyHttpDatas();
                for (InterfaceHttpData data : postList) {
                    List<String> values = new ArrayList<String>();
                    MixedAttribute value = (MixedAttribute) data;
                    value.setCharset(CharsetUtil.UTF_8);
                    values.add(value.getValue());
                    this.parameters.put(data.getName(),values);
                }
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * request body put in {@link #postBody}
     *
     * @param msg
     */
    public void requestBodyHandler(Object msg){
        if (msg instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) msg;
            ByteBuf content = httpContent.content();
            StringBuilder buf = new StringBuilder();
            buf.append(content.toString(CharsetUtil.UTF_8));
            this.postBody = buf.toString();
        }
    }

}
