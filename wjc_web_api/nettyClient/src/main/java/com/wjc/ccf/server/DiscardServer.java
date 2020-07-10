package com.wjc.ccf.server;

import com.wjc.ccf.encode.TimeEncoder;
import com.wjc.ccf.handler.DiscardServerHandler;
import com.wjc.ccf.handler.TimmerServerHandler;
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
 * @date 2019/12/2
 */
public class DiscardServer {

    private int port;
    public DiscardServer(int port){
        this.port = port;
    }

    public void run() throws Exception{
        //NioEventLoopGroup是处理I/O操作的多线程事件循环。Netty EventLoopGroup为不同类型的传输提供了各种实现
        EventLoopGroup server = new NioEventLoopGroup();
        EventLoopGroup user = new NioEventLoopGroup();

        //ServerBootstrap是设置服务器的帮助程序类
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(server, user)
                    //指定使用NioServerSocketChannel用于实例化新类Channel以接受传入连接的类
                    .channel(NioServerSocketChannel.class)
                    //在此指定的处理程序将始终由新接受的评估Channel。该ChannelInitializer是意要帮助用户配置一个新的特殊处理程序Channel
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new DiscardServerHandler()
                                    ,new TimeEncoder(),new TimmerServerHandler());
                        }
                    })
                    //option()用于NioServerSocketChannel接受传入的连接。childOption()是Channel为父级接受的ServerChannel
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            //绑定并开始接受传入的连接
            ChannelFuture channelFuture = b.bind(port).sync();
            //等待服务器套接字关闭。
            //正常关闭服务
            channelFuture.channel().closeFuture().sync();
        }finally {
            server.shutdownGracefully();
            user.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        int port = 9900;
        if(args.length > 0){
            port = Integer.parseInt(args[0]);
        }

        new DiscardServer(port).run();
    }
}
