package com.cazio.netty.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author Ca2io
 * @version 1.0
 * @description Netty客户端
 * @since 2020/4/23 15:13
 */
public class NettyClient {
    public static void main(String[] args) {
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(clientGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("client is ok ...");

            ChannelFuture channelFuture = bootstrap.connect("localhost", 6668).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            clientGroup.shutdownGracefully();
        }
    }
}
