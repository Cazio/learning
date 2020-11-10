package com.cazio.netty.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author Ca2io
 * @version 1.0
 * @description
 * @since 2020/4/23 17:05
 */
public class HttpChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("myHttpServerCoded",new HttpServerCodec());
        pipeline.addLast("myHandler",new HttpHandler());
    }
}
