package com.tuling;

import java.io.IOException;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException {
        NioSocketDemo nioSocketDemo = new NioSocketDemo();
        nioSocketDemo.initServer(8888);
        nioSocketDemo.listenSelector();
    }
}
