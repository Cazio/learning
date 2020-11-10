package com.cazio.netty.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author Ca2io
 * @version 1.0
 * @description 客户端
 * @since 2020/4/21 9:05
 */
public class NIOSocketClient {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9002);
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("连接需要时间,客户端不会阻塞，可以做其他工作");
            }
        }
        String str = "hello";
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes(Charset.defaultCharset()));
        socketChannel.write(byteBuffer);
        System.in.read();
    }
}
