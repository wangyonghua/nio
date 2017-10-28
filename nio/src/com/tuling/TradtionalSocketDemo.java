package com.tuling;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wangyonghua on 2017/10/28.
 */
public class TradtionalSocketDemo {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7777);

        ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println("服务端启动");
        while (true) {
            //获取socket客户端套接字
            Socket socket = serverSocket.accept();
            System.out.println("有新客户端连接上来了");

            executorService.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName());
                    InputStream inputStream = socket.getInputStream();
                    byte[] b = new byte[1024];
                    while (true) {
                        int data = inputStream.read(b);
                        if (data != -1) {
                            String info = new String(b, 0, data, "gbk");
                            System.out.println(info);
                        } else {
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
