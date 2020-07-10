package com.wjc.ccf.test.tcp.server;

import com.wjc.ccf.test.tcp.decode.TcpDecoder;
import com.wjc.ccf.test.tcp.encode.TcpEncoder;
import com.wjc.ccf.test.tcp.handler.TcpInitHandler;
import com.wjc.ccf.test.tcp.handler.TcpReadHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author wjc
 * @description
 * @date 2020/2/28
 */
public class TCPServer {

    public static void main(String[] args) throws Exception{
        int port = 9988;
        EventLoopGroup tcpServer = new NioEventLoopGroup();
        EventLoopGroup client = new NioEventLoopGroup();

        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(tcpServer, client)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addFirst(new TcpEncoder(), new TcpDecoder(), new TcpInitHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 1024);
//                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            tcpServer.shutdownGracefully();
            client.shutdownGracefully();
        }
    }
}
