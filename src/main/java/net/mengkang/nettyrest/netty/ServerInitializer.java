package net.mengkang.nettyrest.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();

        // HttpServerCodec is a combination of HttpRequestDecoder and HttpResponseEncoder
        p.addLast(new HttpServerCodec());

        // add gizp compressor for http response content
        p.addLast(new HttpContentCompressor());

        p.addLast(new HttpObjectAggregator(1048576));

        p.addLast(new ChunkedWriteHandler());

        p.addLast(new ServerHandler());
    }
}

