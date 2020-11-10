package com.cazio.netty.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Ca2io
 * @version 1.0
 * @description
 * @since 2020/4/15 14:20
 */
public class NIOFileChannelA {
    public static void main(String[] args) throws Exception {
        String s = "hello,新冠";

        FileOutputStream fileOutputStream = new FileOutputStream("fc.txt");

        FileChannel fileChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(s.getBytes("UTF-8"));
        byteBuffer.flip();

        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}
