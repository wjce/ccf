package com.wjc.ccf.handler;

import com.wjc.ccf.entity.MyTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wjc
 * @description
 * @date 2019/12/2
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {
//    ByteBuf byteBuf;

//    @Override
//    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        byteBuf = ctx.alloc().buffer(4);
//    }
//
//    @Override
//    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        byteBuf.release();
//        byteBuf = null;
//    }


//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ChannelFuture future = ctx.writeAndFlush(new MyTime());
//        future.addListener(ChannelFutureListener.CLOSE);
//    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf byteBuf = (ByteBuf)msg;
//        this.byteBuf.writeBytes(byteBuf);
        //Netty 4开始，对象的生命周期由它们的引用计数（reference counts）管理，而不是由垃圾收集器（garbage collector）管理了。
        // ByteBuf是最值得注意的，它使用了引用计数来改进分配内存和释放内存的性能。
        //在创建ByteBuf对象后，它的引用计数是1，当释放（release）引用计数对象时，它的引用计数减1，如果引用计数为0，
        // 这个引用计数对象会被释放（deallocate）,并返回对象池。
//        byteBuf.release();
//
//        if(this.byteBuf.readableBytes() >= 4) {
//            long currentTimeMillis = (this.byteBuf.readUnsignedInt() - 2208988800L) * 1000L;
//            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(currentTimeMillis)));
//        }

        //decode
//        if(byteBuf.readableBytes() >= 4) {
//            long currentTimeMillis = (byteBuf.readUnsignedInt() - 2208988800L) * 1000L;
//            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(currentTimeMillis)));
//        }

        MyTime myTime = (MyTime) msg;
        System.out.println(myTime.toString());
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
