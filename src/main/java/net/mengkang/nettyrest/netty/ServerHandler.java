package net.mengkang.nettyrest.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.AsciiString;
import io.netty.handler.codec.http.*;

import net.mengkang.nettyrest.ApiHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


public class ServerHandler extends ChannelHandlerAdapter {
    private final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;

            if (HttpHeaderUtil.is100ContinueExpected(req)) {
                ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
            }
            boolean keepAlive = HttpHeaderUtil.isKeepAlive(req);

            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(ApiHandler.transfer(ctx,msg)));

            response.headers().set(CONTENT_TYPE,new AsciiString("application/json; charset=utf-8"));
            response.headers().set(TRANSFER_ENCODING, HttpHeaderValues.CHUNKED);

            if (!keepAlive) {
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            } else {
                response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                ctx.write(response);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
        logger.error("ctx close!");
    }

}
