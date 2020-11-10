package com.cazio.netty.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Ca2io
 * @version 1.0
 * @description
 * @since 2020/4/20 13:58
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("fc.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 6);
        map.put(0, (byte) 'H');
        map.put(3, (byte) 'E');
        map.put(4, (byte) 'f');

        channel.close();
        randomAccessFile.close();
    }
}
