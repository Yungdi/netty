package com.yun27jin.netty.chapter2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }


    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: " + EchoServer.class.getSimpleName() + " <port>");
        }
        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }

    private void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        try {
            b.group(group)
                    .channel(NioServerSocketChannel.class)       // nio 전송 채널을 이용하도록 지정
                    .localAddress(new InetSocketAddress(port))   // 지정된 포트를 이용해 소켓 주소를 설정
                    .childHandler(new ChannelInitializer<>() {   // ChannelPipeline 에 ChannelHandler 추가
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(serverHandler);
                        }
                    });
            ChannelFuture f = b.bind().sync(); // 서버를 비동기식으로 바인딩. sync 는 바인딩이 완료되기를 대기
            f.channel().closeFuture().sync(); // 채널의 CloseFuture 를얻고 완료될 때까지 현재 스레드를 블로킹
        } finally {
            group.shutdownGracefully().sync(); // EventLoopGroup 를 종료하고 모든 리소스를 해
        }
    }
}
