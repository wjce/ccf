package com.wjc.ccf.test.tcp.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @author wjc
 * @description
 * @date 2020/2/28
 */
public class TcpReadHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            System.out.println(new String((byte[]) msg));
//            ByteBuf byteBuf = (ByteBuf) msg;
//            int length = byteBuf.readableBytes();
//            byte[] bytes = new byte[length];
//            byteBuf.readBytes(bytes);
//            System.out.println(new String(bytes));
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("server hi 2, " + System.currentTimeMillis());
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("server hi 1, " + System.currentTimeMillis());
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable){
        throwable.printStackTrace();
        channelHandlerContext.close();
    }
}
