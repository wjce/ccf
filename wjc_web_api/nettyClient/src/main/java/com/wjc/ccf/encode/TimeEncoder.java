package com.wjc.ccf.encode;

import com.wjc.ccf.entity.MyTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author wjc
 * @description
 * @date 2019/12/2
 */
public class TimeEncoder extends MessageToByteEncoder<MyTime> {

//    @Override
//    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//        MyTime myTime = (MyTime) msg;
//        ByteBuf byteBuf = ctx.alloc().buffer(4);
//        byteBuf.writeInt((int)myTime.value());
//        ctx.write(byteBuf, promise);
//    }

    //会自动进行flush
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MyTime myTime, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt((int)myTime.value());
    }
}
