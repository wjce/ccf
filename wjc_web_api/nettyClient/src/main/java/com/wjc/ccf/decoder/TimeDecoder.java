package com.wjc.ccf.decoder;

import com.wjc.ccf.entity.MyTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author wjc
 * @description
 * @date 2019/12/2
 */
//ByteToMessageDecoder是一个实现ChannelInboundHandler，可以轻松处理碎片问题
public class TimeDecoder extends ByteToMessageDecoder {

    //ByteToMessageDecoderdecode()每当接收到新数据时，都使用内部维护的累积缓冲区调用该方法
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //decode()可以决定out不向累积缓冲区中没有足够数据的位置添加任何内容。收到更多数据时ByteToMessageDecoder将decode()再次调用
        if(byteBuf.readableBytes() < 4){
            return;
        }

        //如果decode()将对象添加到out，则表示解码器成功解码了一条消息。ByteToMessageDecoder将丢弃累积缓冲区的读取部分。
        // 不需要解码多条消息。ByteToMessageDecoder会一直调用该decode()方法，直到该方法不添加任何内容out
//        list.add(byteBuf.readBytes(4));
        //改为POJO形式
        list.add(new MyTime(byteBuf.readUnsignedInt()));
    }

}
