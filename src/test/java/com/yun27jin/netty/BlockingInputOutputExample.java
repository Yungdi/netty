package com.yun27jin.netty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class BlockingInputOutputExample {
    public static void main(String[] args) throws IOException {
        final int PORT = 9201;
        ServerSocket serverSocket = new ServerSocket(PORT);
        Socket clientSocket = serverSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        String request, response;
        while ((request = in.readLine()) != null) {
            System.out.println(request);
        }
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress("localhost", 9200));

    }
}
