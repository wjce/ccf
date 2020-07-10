package com.wjc.ccf.listener;

import com.rabbitmq.client.*;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author wjc
 * @description
 * @date 2019/8/9
 */
@Component
@RabbitListener(queues = "DIRECT_QUEUE")
public class RabbitMqListener {
    @RabbitHandler
    public void processMessage(String msg) throws Exception{
        System.out.println("DIRECT_QUEUE:   " + msg);
        System.out.println("DIRECT_QUEUE:   " + System.currentTimeMillis());
//        System.out.println(message.getBody());
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
