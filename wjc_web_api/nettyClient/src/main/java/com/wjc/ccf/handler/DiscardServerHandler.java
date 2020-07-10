package com.wjc.ccf.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @description 处理服务器端通道
 * @author wjc
 * @date 2018/10/11
 */
//ChannelInboundHandler提供了可以覆盖的各种事件处理程序方法
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    //每当从客户端接收到新数据时，就使用接收到的消息调用此方法

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg){
//        channelHandlerContext.write(msg);
//        channelHandlerContext.flush();
        ByteBuf byteBuf = ((ByteBuf)msg);
        try{
            while(byteBuf.isReadable()){
                System.out.println("----------------" + (char)byteBuf.readByte());
                System.out.flush();
            }
        }finally {
            //释放所有传递到处理器的引用计数对象
            ReferenceCountUtil.release(msg);
        }
    }

    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable){
        throwable.printStackTrace();
        channelHandlerContext.close();
    }
}
