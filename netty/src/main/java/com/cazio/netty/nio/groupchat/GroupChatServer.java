package com.cazio.netty.nio.groupchat;


import java.io.IOException;
import java.lang.annotation.Target;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author Ca2io
 * @version 1.0
 * @description 群聊服务器
 * @since 2020/4/21 10:09
 */
public class GroupChatServer {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private static final int PORT = 3001;

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
    public GroupChatServer() {
        try {
            //1.创建selector
            selector = Selector.open();
            //2.配置serversocketChannel
            serverSocketChannel = ServerSocketChannel.open();
            //2.1设置非阻塞
            serverSocketChannel.configureBlocking(false);
            //2.2绑定端口
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            //3.注册到select
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启监听
     */
    private void listen() {
        while (true) {
            try {
                int count = selector.select(2000);
                if (count > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        if (selectionKey.isAcceptable()) {
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() + " 上线 ");
                        } else if (selectionKey.isReadable()) {
                            readData(selectionKey);
//                        } else if (selectionKey.isWritable()) {
//                            wirteData(selectionKey);
                        }
                        iterator.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void wirteData(SelectionKey selectionKey) {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        String message = (String) selectionKey.attachment();
        try {
            socketChannel.write(ByteBuffer.wrap(message.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取socket里的数据
     *
     * @param selectionKey
     */
    private void readData(SelectionKey selectionKey) {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int count = socketChannel.read(byteBuffer);
            if (count > 0) {
                String msg = new String(byteBuffer.array());
                System.out.println(socketChannel.getRemoteAddress() + "say:" + msg);
                transferToOther(msg, socketChannel);
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                System.out.println(socketChannel.getRemoteAddress() + "离线了");
                selectionKey.cancel();
                socketChannel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 转发给其他客户端
     *
     * @param msg
     * @param socketChannel
     */
    private void transferToOther(String msg, SocketChannel socketChannel) {
        System.out.println("转发消息");
        for(SelectionKey key:selector.keys()){
            Channel channel = key.channel();
            if (!(channel instanceof SocketChannel)||channel.equals(socketChannel)) {
                continue;
            }
            SocketChannel schannel = (SocketChannel) channel;
            try {
                schannel.configureBlocking(false);
                schannel.write(ByteBuffer.wrap(msg.getBytes()));
//                channel.register(selector, SelectionKey.OP_WRITE, msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
