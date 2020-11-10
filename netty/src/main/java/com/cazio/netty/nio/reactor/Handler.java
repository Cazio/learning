package com.cazio.netty.nio.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author Ca2io
 * @version 1.0
 * @description Acceptor
 * @since 2020/4/22 9:34
 */
public class Handler implements Runnable {
    final private SelectionKey selectionKey;
    final private SocketChannel socketChannel;
    ByteBuffer input = ByteBuffer.allocate(1024);
    ByteBuffer output = ByteBuffer.allocate(1024);

    static final int READING = 0, SENDING = 1;
    int state = READING;

    public Handler(Selector selector, SocketChannel socketChannel) throws IOException {
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
        //TODO
    }
//
//    @Override
//    public void run() {
//        try {
//            if (state == READING) {
//                read();
//            } else if (state == SENDING) {
//                send();
//            }
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//
//    }

    @Override
    public void run() {
        try {
            socketChannel.read(input);
            if (inputIsComplete()) {
                process();
                selectionKey.attach(new Sender());
                selectionKey.attach(SelectionKey.OP_WRITE);
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

    private void read() throws IOException {
        socketChannel.read(input);
        if (inputIsComplete()) {
            process();
            state = SENDING;
            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }
    }
}
