package com.cazio.netty.nio.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.*;

import static javax.swing.text.html.HTML.Tag.OL;

/**
 * @author Ca2io
 * @version 1.0
 * @description 单Reactor多线程
 * @since 2020/4/22 9:34
 */
public class Handler2 implements Runnable {
    final private SelectionKey selectionKey;
    final private SocketChannel socketChannel;
    ByteBuffer input = ByteBuffer.allocate(1024);
    ByteBuffer output = ByteBuffer.allocate(1024);

    static final int READING = 0, SENDING = 1, PROCESSING = 2;
    int state = READING;
    static ExecutorService pool = new ThreadPoolExecutor(5, 200,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<Runnable>(1024),
            new ThreadPoolExecutor.AbortPolicy());

    public Handler2(Selector selector, SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
        socketChannel.configureBlocking(false);
        selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
        selectionKey.attach(this);
        selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    boolean inputIsComplete() {
        return false;
    }

    boolean outputIsComplete() {
        return true;
    }

    void process() {
        //模拟IO阻塞
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            socketChannel.read(input);
            if (inputIsComplete()) {
                process();
                selectionKey.attach(new Sender());
                selectionKey.interestOps(SelectionKey.OP_WRITE);
                selectionKey.selector().wakeup();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Sender implements Runnable {

        @Override
        public void run() {
            try {
                socketChannel.write(output);
                if (outputIsComplete()) {
                    selectionKey.cancel();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void send() throws IOException {
        socketChannel.write(output);
        if (outputIsComplete()) {
            selectionKey.cancel();
        }
    }

    private synchronized void read() throws IOException {
        socketChannel.read(input);
        if (inputIsComplete()) {
            state = PROCESSING;
            pool.execute(new Processer());
            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }
    }

    class Processer implements Runnable {
        @Override
        public void run() {
            processAndHandOff();
        }
    }

    private void processAndHandOff() {
        process();
        state = SENDING;
        selectionKey.interestOps(SelectionKey.OP_WRITE);
        selectionKey.selector().wakeup();
    }
}
