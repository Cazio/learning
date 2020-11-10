package com.cazio.netty.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author Ca2io
 * @version 1.0
 * @description 分散与收集的描述
 * @since 2020/4/20 14:09
 */
public class ScattringAndGatheringTest {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetAddress = new InetSocketAddress(9000);
        serverSocketChannel.socket().bind(inetAddress);

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(3);
        byteBuffers[1] = ByteBuffer.allocate(5);
        int messageLenth = 8;
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            int byteRead = 0;
            while (byteRead < messageLenth) {

                long l = socketChannel.read(byteBuffers);
                byteRead += l;
                System.out.println("byteRead =" + byteRead);
                Arrays.asList(byteBuffers).stream().map(buffer ->
                        "position =" + buffer.position() + ",limit=" +
                                buffer.limit()).forEach(System.out::println);
                Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.flip());

            }
            long byteWrite = 0;
            while (byteWrite < messageLenth) {
                long s = socketChannel.write(byteBuffers);
                byteWrite += s;
            }
            Arrays.asList(byteBuffers).forEach(byteBuffer -> {
                byteBuffer.clear();
            });
        }
    }
}
