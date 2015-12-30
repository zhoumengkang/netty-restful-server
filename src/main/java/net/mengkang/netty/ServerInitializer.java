package net.mengkang.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();

        // 实际是做了HttpRequestDecoder的操作
        p.addLast(new HttpServerCodec());
        // 添加 gzip 压缩
        p.addLast(new HttpContentCompressor());

        // 如果你希望为一个单一的HTTP消息处理多个消息，你可以把HttpObjectAggregator放入管道里。HttpObjectAggregator会把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse
        p.addLast(new HttpObjectAggregator(1048576));

        p.addLast(new ChunkedWriteHandler());

        p.addLast(new ServerHandler());
    }
}

