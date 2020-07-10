package com.wjc.ccf.listener;

import com.rabbitmq.client.Channel;
import com.wjc.ccf.config.RabbitConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author wjc
 * @description
 * @date 2019/8/12
 */
@Component
public class DeadListener {
    @RabbitListener(queues = RabbitConfig.DEAD_LETTER_QUEUE)
    @RabbitHandler
    public void testDeadLetterQueueAndThrowsException(String msg) throws Exception{
        System.out.println("TDL_QUEUE:    " + msg);
        System.out.println("TDL_QUEUE:    " + System.currentTimeMillis());
//        throw new RuntimeException("exception");
//        System.out.println(message.getBody());
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = RabbitConfig.REDIRECT_QUEUE)
    @RabbitHandler
    public void testRedirectQueue(String msg){
        System.out.println("TREDIRECT_QUEUE:   " + System.currentTimeMillis());
        System.out.println("TREDIRECT_QUEUE:   " + msg);
    }
}
