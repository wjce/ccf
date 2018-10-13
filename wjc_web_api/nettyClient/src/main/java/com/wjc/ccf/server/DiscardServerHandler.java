package com.wjc.ccf.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author wangjunce 2018/10/11 16:14
 */
public class DiscardServerHandler extends ChannelHandlerAdapter {

    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg){
        ((ByteBuf)msg).release();
    }

    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable){
        throwable.printStackTrace();
        channelHandlerContext.close();
    }
}
