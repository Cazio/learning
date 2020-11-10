package com.cazio.netty.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Ca2io
 * @version 1.0
 * @description
 * @since 2020/4/20 17:28
 */
public class NIOSocketServer {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChnannel = ServerSocketChannel.open();
        serverSocketChnannel.socket().bind(new InetSocketAddress(9002));
        serverSocketChnannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverSocketChnannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
//            selector.select();
            if (selector.select(1000) == 0) {
                System.out.println("等待超时~");
                continue;
            }
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeySet.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChnannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                } else if (selectionKey.isReadable()) {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    channel.read(byteBuffer);
//                    while (byteBuffer.hasRemaining()) {
//                        System.out.println(byteBuffer.get());
//                    }
                    System.out.println("客户端"+new String(byteBuffer.array()));
                }
                iterator.remove();
//                doSelectOne((ServerSocketChannel) selectionKey.channel());
            }
        }
    }

    private static void doSelectOne(ServerSocketChannel channel) throws IOException {
        SocketChannel socketChannel = channel.accept();
        socketChannel.write(Charset.defaultCharset().encode("HelloWorld"));
    }
}
