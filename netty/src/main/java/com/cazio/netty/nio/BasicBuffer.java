package com.cazio.netty.nio;

import java.nio.IntBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author Ca2io
 * @version 1.0
 * @description NIOBuffer
 * @since 2020/4/15 11:27
 */
public class BasicBuffer {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(10);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put((i + 1) * 2);
        }
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }

    }
}
