package com.cazio.netty.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Ca2io
 * @version 1.0
 * @description 使用一个Buffer缓冲区进行读写文件
 * @since 2020/4/15 14:40
 */
public class NIOFileOneBuffer {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("fc.txt");
        FileChannel inChannel = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("out.txt");
        FileChannel outChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (true) {
            byteBuffer.clear();

            int capcity = inChannel.read(byteBuffer);
            if (capcity == -1) {
                break;
            }
            byteBuffer.flip();
            int capcity2 = outChannel.write(byteBuffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
