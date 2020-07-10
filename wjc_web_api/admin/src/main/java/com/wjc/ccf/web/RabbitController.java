package com.wjc.ccf.web;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wjc
 * @description
 * @date 2019/8/9
 */
@RestController
@RequestMapping("/rabbit")
public class RabbitController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping("/send")
    public String send(){

        try {

            logger.info("asdfasdfsd");
            amqpTemplate.convertAndSend("TDL_EXCHANGE", "TDL_KEY", "aaaaaa");
            System.out.println(System.currentTimeMillis());
//            amqpTemplate.convertAndSend("DL_EXCHANGE", "REDIRECT_QUEUE", "6666");
            MessagePostProcessor messagePostProcessor = message -> {
                MessageProperties messageProperties = message.getMessageProperties();
//            设置编码
                messageProperties.setContentEncoding("utf-8");
//            设置过期时间10*1000毫秒
                messageProperties.setExpiration("100000");
                return message;
            };
            for (int i = 0; i < 10; i++) {
                rabbitTemplate.convertAndSend( "DIRECT_EXCHANGE",  "DIRECT_ROUTING_KEY",""+i, messagePostProcessor);
                System.out.println("current: "+i);
//                Thread.sleep(1000l);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "send success";
    }

    @GetMapping("/receive")
    public String receive(){
        Message message = rabbitTemplate.receive("DIRECT_QUEUE");
        String msg = "";
        if (StringUtils.isNotBlank(msg)){
            msg = message.getBody().toString();
            System.out.println(msg);
            return "receive message";
        }

        return "receive null";
    }

    public static void main(String[] args) {
        System.out.println(1586835669539l-1586835669519l);
    }
}
