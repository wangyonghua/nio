package com.tuling;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * Created by wangyonghua on 2017/10/28.
 */
public class NioSocketDemo {
    private Selector selector; //通道选择器(管理器)


    public void initServer(int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(port));
        this.selector = Selector.open();
        serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务启动");
    }

    public void listenSelector() throws IOException {
        while (true) {
            //等待客户连接
            //select模型，多路复用
            this.selector.select();
            Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                iterator.remove();
                handler(next);
            }
        }
    }

    private void handler(SelectionKey key) throws IOException {
        //处理客户端连接请求事件
        if (key.isAcceptable()) {
            ServerSocketChannel channel = (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = channel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
        } else if (key.isReadable()) {
            //处理读的事件
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int read = socketChannel.read(buffer);
            if (read > 0) {
                String info = new String(buffer.array(), "GBK").trim();
                System.out.println("服务端收到数据:" + info);
            } else {
                System.out.println("关闭了");
                key.cancel();
            }
        }
    }
}
